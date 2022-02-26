package com.ibm.academia.restapi.ipfraude.modelo.mapper;

import java.util.Map;

import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryRateDTO;

public class FixerMapper {
	/**
	 * Mapeo para obtener la cotizacion de la lista
	 * @param rates Es un map que tiene la llave de la moneda y su valor, que puede ser Integer o Double
	 * @param coin Llave para buscar en rates
	 * @return CountryRateDTO
	 */
	public static CountryRateDTO mapWithCoin(Map<String, ?> rates, String coin) {
		CountryRateDTO countryDTO = new CountryRateDTO();
		countryDTO.setCotizacion( Double.valueOf(rates.get(coin).toString()) );
		return countryDTO;
	}
}