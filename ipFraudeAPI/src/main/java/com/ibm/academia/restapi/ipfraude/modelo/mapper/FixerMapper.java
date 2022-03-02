package com.ibm.academia.restapi.ipfraude.modelo.mapper;

import java.util.Map;

import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryRateDTO;

public class FixerMapper {
	/**
	 * Mapping for obtaining the a rate value from all the list
	 * @param rates A Map with String keys and values that can be Integer or Double
	 * @param coin Key for search in the Map
	 * @return A DTO for saving the rate searched value
	 */
	public static CountryRateDTO mapWithCoin(Map<String, ?> rates, String coin) {
		CountryRateDTO countryRateDTO = new CountryRateDTO();
		countryRateDTO.setRate( Double.valueOf(rates.get(coin).toString()) );
		return countryRateDTO;
	}
}
