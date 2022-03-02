package com.ibm.academia.restapi.ipfraude.modelo.mapper;

import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryDTO;
import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryCoinDTO;
import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryRateDTO;
import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryNameDTO;

public class CountryMapper {
	
	public static CountryDTO mapFromIPAndInfo(CountryNameDTO ipDTO, CountryCoinDTO layerDTO, CountryRateDTO fixerDTO) {
		CountryDTO countryDTO = new CountryDTO();
		countryDTO.setName(ipDTO.getName());
		countryDTO.setISO(ipDTO.getISO());
		countryDTO.setCoin(layerDTO.getCoin());
		countryDTO.setRate(fixerDTO.getRate());
		return countryDTO;
	}
}
