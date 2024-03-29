package com.ibm.academia.restapi.ipfraude.clientes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryRateDTO;
import com.ibm.academia.restapi.ipfraude.modelo.mapper.FixerMapper;

/**
 * @author EMHH 24-02-2022
 */
public class FixerClientTest {
	
	@Autowired
	private FixerRest fixerRest;
	
	@Value("${fixer.api-key}")
	private String apiKey;
	
	@Test
	@DisplayName("Buscando cotizacion con un codigo alpha3 correcto")
	void findCountryByCoin() {
		//GWT
		//Given
		String code = "EUR";
		//When
		Map<String,Object> fixerResponse = fixerRest.getAllBaseDefault(apiKey);
		@SuppressWarnings("unchecked")
		Map<String, Double> rates = (Map<String, Double>) fixerResponse.get("rates");
		
		CountryRateDTO expected = FixerMapper.mapWithCoin(rates, code);
		//Then
		assertThat(expected.getRate() instanceof Double).isTrue();
	}
	
	@Test
	@DisplayName("Buscando cotizacion con un codigo alpha3 correcto, diferente a la base")
	void findCountryByDiferentBaseCoin() {
		//GWT
		//Given
		String code = "USD";
		//When
		Map<String,Object> fixerResponse = fixerRest.getAllBaseDefault(apiKey);
		@SuppressWarnings("unchecked")
		Map<String, Double> rates = (Map<String, Double>) fixerResponse.get("rates");
		
		CountryRateDTO expected = FixerMapper.mapWithCoin(rates, code);
		//Then
		assertThat(expected.getRate() instanceof Double).isTrue();
	}
	
	@Test
	@DisplayName("Buscando cotizacion con un codigo alpha3 incorrecto o no existente")
	@Disabled
	void findCountryByBadCoin() {
		//GWT
		//Given
		String code = "EURasa";
		//When
		Map<String,Object> fixerResponse = fixerRest.getAllBaseDefault(apiKey);
		@SuppressWarnings("unchecked")
		Map<String, Double> rates = (Map<String, Double>) fixerResponse.get("rates");
		
		CountryRateDTO expected = FixerMapper.mapWithCoin(rates, code);
		//Then
		assertThat(expected.getRate()).isEqualTo(null);
	}
}
