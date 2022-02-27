package com.ibm.academia.restapi.ipfraude.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryDTO;
import com.ibm.academia.restapi.ipfraude.modelo.servicios.IPService;

/**
 * @author EMHH 24-02-2022
 */
@SpringBootTest
public class IPServiceImplTest {
	
	@Autowired
	private IPService ipService;
	
	@Test
	@DisplayName("Buscando un pais con el IP correcto")
	void findCountryByIP() {
		//GWT
		//Given
		String ip = "5.6.7.8";
		//When
		CountryDTO expected = ipService.getCountryInfoByIP(ip);
		//Then
		assertThat(expected.getISO()).isEqualTo("DEU");
		assertThat(expected.getMoneda()).isEqualTo("EUR");
		assertThat(expected.getNombre()).isEqualTo("Germany");
		assertThat(expected.getCotizacion()).isEqualTo(1.0);
	}
	
	@Test
	@DisplayName("Buscando un pais con el IP incorrecto o no existente")
	void findCountryByIPBadIP() {
		//GWT
		//Given
		String ip = "5.6.7.812121";
		//When
		CountryDTO expected = new CountryDTO();
		try {
			expected = ipService.getCountryInfoByIP(ip);
		} catch (Exception e) {
//			if(e instanceof FeignException.BadRequest) {
////				expected.setMoneda(e.getMessage());
//				throw new BadRequestExternalApiException(e.getMessage());
//			}
		}
		//Then
		assertThat(expected.getISO()).isEqualTo(null);
		assertThat(expected.getMoneda()).isEqualTo(null);
		assertThat(expected.getNombre()).isEqualTo(null);
		assertThat(expected.getCotizacion()).isEqualTo(null);
	}
}
