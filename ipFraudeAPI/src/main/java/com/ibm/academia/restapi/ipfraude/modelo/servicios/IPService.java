package com.ibm.academia.restapi.ipfraude.modelo.servicios;

import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryDTO;
import com.ibm.academia.restapi.ipfraude.modelo.entidades.BlackList;

public interface IPService {
	public BlackList banIP(String ip);
	public BlackList unbanIP(String ip);
	public CountryDTO getCountryInfoByIP(String ip);
}