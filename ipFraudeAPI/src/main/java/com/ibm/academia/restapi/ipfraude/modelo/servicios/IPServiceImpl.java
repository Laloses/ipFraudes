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
	 * Saves a new value in the BlackList repository
	 * @param ip Value for the IP to save
	 * @return An object BlackList
	 * @throws AlreadyExistException When the value to save already exist
	 * @author EMHH 25-02-2022
	 */
	@Override
	public BlackList banIP(String ip) {
		Optional<BlackList> blOptional = blackListRepository.findByIpAndBanned(ip,true);
		if(blOptional.isPresent())
			throw new AlreadyExistException("Already exist that IP in the black list.");
		
		CountryDTO countryDTO = getCountryInfoByIP(ip);
		BlackList blackList = new BlackList(null,ip,true,countryDTO);
		
		return blackListRepository.save(blackList);
	}

	/**
	 * Remove the ban for a determinate IP
	 * @param ip Value for the IP to unban
	 * @return An object BlackList
	 * @throws NotFoundException When the value not exists
	 * @author EMHH 25-02-2022
	 */
	@Override
	public BlackList unbanIP(String ip) {
		Optional<BlackList> blOptional = blackListRepository.findByIpAndBanned(ip,true);
		if(!blOptional.isPresent())
			throw new NotFoundException("There is no banned record with that IP.");
		
		blOptional.get().setBanned(false);
		
		return blackListRepository.save(blOptional.get());	
	}

	/**
	 * Search information of a country, with a given IP using endpoints of Feign clients
	 * @param ip IP that belongs to a country
	 * @return CountryDTO
	 * @throws NotAllowedException When the IP is banned
	 * @throws BadRequestExternalApiException When some endpint fails
	 * @throws NotFoundException When there is no information in the response
	 * @author EMHH 25-02-2022
	 */
	@Override
	public CountryDTO getCountryInfoByIP(String ip) {
		Optional<BlackList> blOptional = blackListRepository.findByIpAndBanned(ip, true);
		
		if(blOptional.isPresent())
			throw new NotAllowedException("This IP does not have permission.");
		
		CountryNameDTO cNameDTO = clientIP.findCountryByIP(ip);
		CountryCoinDTO coinDTO = countryRest.findCountryByCode3(cNameDTO.getISO());
		
		if(coinDTO.getCoin() == null)
			throw new BadRequestExternalApiException("Error when getting the coin of the country.");
		
		Map<String, Object> responseFixer = clientFixerRest.getAllBaseDefault(apiKeyFixer);
		
		@SuppressWarnings("unchecked")
		Map<String, Double> rates = (Map<String, Double>) responseFixer.get("rates");
		
		if(!rates.containsKey(coinDTO.getCoin()))
			throw new NotFoundException("The coin code of that country does not have a rate.");
		
		CountryRateDTO cRateDTO = FixerMapper.mapWithCoin(rates, coinDTO.getCoin());
		
		return CountryMapper.mapFromIPAndInfo(cNameDTO, coinDTO, cRateDTO);
	}
}