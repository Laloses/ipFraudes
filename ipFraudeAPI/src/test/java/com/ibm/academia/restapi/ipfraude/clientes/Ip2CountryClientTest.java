package com.ibm.academia.restapi.ipfraude.clientes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryNameDTO;

public class Ip2CountryClientTest {
	
	@Autowired
	private Ip2CountryRest ip2CountryRest;
	
	@Test
	@DisplayName("Buscando con una ip correcta")
	void findCountryByCode3() {
		//GWT
		//Given
		String ip = "5.6.7.8";
		//When
		CountryNameDTO expected = ip2CountryRest.findCountryByIP(ip);
		//Then
		assertThat(expected.getISO()).isEqualTo("DEU");
		assertThat(expected.getName()).isEqualTo("Germany");
	}
	
	@Test
	@DisplayName("Ip mal enviado o no existente")
	void findCountryByBadCode3() {
		//GWT
		//Given
		String ip = "5.6.7.812321312";
		//When
		CountryNameDTO expected = new CountryNameDTO();
		try {
			expected = ip2CountryRest.findCountryByIP(ip);
		} catch (Exception e) {
//			if(e instanceof FeignException.BadRequest) {
//			expected.setNombre(e.getMessage());
//			throw new BadRequestExternalApiException(e.getMessage());
//			}
		}
		//Then
		assertThat(expected.getISO()).isEqualTo(null);
		assertThat(expected.getName()).isEqualTo(null);
	}
}
