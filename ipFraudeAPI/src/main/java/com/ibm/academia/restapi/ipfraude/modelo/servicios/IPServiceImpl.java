package com.ibm.academia.restapi.ipfraude.modelo.servicios;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ibm.academia.restapi.ipfraude.clientes.CountryRest;
import com.ibm.academia.restapi.ipfraude.clientes.FixerRest;
import com.ibm.academia.restapi.ipfraude.clientes.Ip2CountryRest;
import com.ibm.academia.restapi.ipfraude.excepciones.AlreadyExistException;
import com.ibm.academia.restapi.ipfraude.excepciones.BadRequestExternalApiException;
import com.ibm.academia.restapi.ipfraude.excepciones.NotAllowedException;
import com.ibm.academia.restapi.ipfraude.excepciones.NotFoundException;
import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryCoinDTO;
import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryDTO;
import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryNameDTO;
import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryRateDTO;
import com.ibm.academia.restapi.ipfraude.modelo.entidades.BlackList;
import com.ibm.academia.restapi.ipfraude.modelo.mapper.CountryMapper;
import com.ibm.academia.restapi.ipfraude.modelo.mapper.FixerMapper;
import com.ibm.academia.restapi.ipfraude.modelo.repositorios.BlackListRepository;

@Service
public class IPServiceImpl implements IPService {
	
	@Autowired
	private BlackListRepository blackListRepository;
	
	@Autowired
	private Ip2CountryRest clientIP;
	
	@Autowired
	private CountryRest countryRest;
	
	@Autowired
	private FixerRest clientFixerRest;
	
	@Value("${fixer.api-key}")
	private String apiKeyFixer;


	/**
	 * Guarda un nuevo valor de IP dentro de la lista negra
	 * @param ip Valor de IP a guardar
	 * @return BlackList
	 * @author EMHH 25-02-2022
	 */
	@Override
	public BlackList banIP(String ip) {
		Optional<BlackList> blOptional = blackListRepository.findByIp(ip);
		if(blOptional.isPresent())
			throw new AlreadyExistException("Ya existe esa IP en la lista negra.");
		
		CountryDTO countryDTO = getCountryInfoByIP(ip);
		BlackList blackList = new BlackList(null,ip,countryDTO);
		
		BlackList blackListSaved = blackListRepository.save(blackList);
		return blackListSaved;
	}

	/**
	 * Busca infomacion variada de un país, en base a su ip utilizando consultas a clientes Feign
	 * @param ip IP pertenenciente a un país
	 * @return CountryDTO
	 * @author EMHH 25-02-2022
	 */
	@Override
	public CountryDTO getCountryInfoByIP(String ip) {
		Optional<BlackList> blOptional = blackListRepository.findByIp(ip);
		
		if(blOptional.isPresent())
			throw new NotAllowedException("La IP consultada no tiene permiso");
		
		CountryNameDTO cNameDTO = clientIP.findCountryByIP(ip);
		CountryCoinDTO coinDTO = countryRest.findCountryByCode3(cNameDTO.getISO());
		
		if(coinDTO.getMoneda() == null)
			throw new BadRequestExternalApiException("Error al obtener la moneda del pais");
		
		Map<String, Object> responseFixer = clientFixerRest.getAllBaseDefault(apiKeyFixer);
		Map<String, Double> rates = (Map<String, Double>) responseFixer.get("rates");
		
		if(!rates.containsKey(coinDTO.getMoneda()))
			throw new NotFoundException("No existe el codigo de moneda para ese país");
		
		CountryRateDTO cRateDTO = FixerMapper.mapWithCoin(rates, coinDTO.getMoneda());
		
		return CountryMapper.mapFromIPAndInfo(cNameDTO, coinDTO, cRateDTO);
	}
}