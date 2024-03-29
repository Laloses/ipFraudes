package com.ibm.academia.restapi.ipfraude.clientes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryCoinDTO;

/**
 * @author EMHH 24-02-2022
 */
public class CountryRestTest {
	
	@Autowired
	private CountryRest countryRest;
	
	@Test
	@DisplayName("Buscando con un codigo alpha correcto")
	void findCountryByIP() {
		//GWT
		//Given
		String code = "DEU";
		//When
		CountryCoinDTO expected = countryRest.findCountryByCode3(code);
		//Then
		assertThat(expected.getCoin()).isEqualTo("EUR");
	}
	
	@Test
	@DisplayName("Buscando con un codigo alpha incorrecto o no existente")
	void findCountryByIPBadIP() {
		//GWT
		//Given
		String code = "DEUasas";
		//When
		CountryCoinDTO expected = new CountryCoinDTO();
		try {
			expected = countryRest.findCountryByCode3(code);
		} catch (Exception e) {
//			if(e instanceof FeignException.BadRequest) {
//				expected.setMoneda(e.getMessage());
//				throw new BadRequestExternalApiException(e.getMessage());
//			}
		}
		//Then
		assertThat(expected.getCoin()).isEqualTo(null);
	}
}
