package com.ibm.academia.restapi.ipfraude.modelo.servicios;

import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryDTO;

import org.springframework.stereotype.Service;

import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryCoinDTO;
import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryRateDTO;
import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryNameDTO;
import com.ibm.academia.restapi.ipfraude.modelo.entidades.BlackList;

public interface IPService {
	public BlackList banIP(String ip);
	public CountryDTO getCountryInfoByIP(String ip);
}