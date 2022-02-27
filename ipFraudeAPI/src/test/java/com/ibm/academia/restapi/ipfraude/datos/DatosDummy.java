package com.ibm.academia.restapi.ipfraude.datos;

import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryDTO;
import com.ibm.academia.restapi.ipfraude.modelo.entidades.BlackList;

public class DatosDummy {
	public static CountryDTO country01() {
		CountryDTO countryDTO = new CountryDTO();
		countryDTO.setCotizacion(0.05);
		countryDTO.setISO("MXN");
		countryDTO.setMoneda("MXN");
		countryDTO.setNombre("Mexico");
		return countryDTO;
	}
	public static CountryDTO countryGermany() {
		CountryDTO countryDTO = new CountryDTO();
		countryDTO.setCotizacion(Double.valueOf(1));
		countryDTO.setISO("DEU");
		countryDTO.setMoneda("EUR");
		countryDTO.setNombre("Germany");
		return countryDTO;
	}
	
	public static BlackList blackList01() { return new BlackList(null,"1.2.3.4",country01());}
	
}
