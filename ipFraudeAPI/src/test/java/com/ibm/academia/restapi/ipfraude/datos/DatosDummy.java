package com.ibm.academia.restapi.ipfraude.datos;

import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryDTO;
import com.ibm.academia.restapi.ipfraude.modelo.entidades.BlackList;

public class DatosDummy {
	public static CountryDTO country01() {
		CountryDTO countryDTO = new CountryDTO();
		countryDTO.setRate(0.05);
		countryDTO.setISO("MXN");
		countryDTO.setCoin("MXN");
		countryDTO.setName("Mexico");
		return countryDTO;
	}
	public static CountryDTO countryGermany() {
		CountryDTO countryDTO = new CountryDTO();
		countryDTO.setRate(Double.valueOf(1));
		countryDTO.setISO("DEU");
		countryDTO.setCoin("EUR");
		countryDTO.setName("Germany");
		return countryDTO;
	}
	
	public static BlackList blackList01() { return new BlackList(null,"1.2.3.4", true, country01());}
	public static BlackList blackList02() { return new BlackList(null,"1.2.3.4", false, countryGermany());}
	
}
