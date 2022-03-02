package com.ibm.academia.restapi.ipfraude.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ibm.academia.restapi.ipfraude.modelo.entidades.BlackList;
import com.ibm.academia.restapi.ipfraude.modelo.repositorios.BlackListRepository;
import com.ibm.academia.restapi.ipfraude.datos.DatosDummy;

/**
 * @author EMHH 24-02-2022
 */
@DataJpaTest
public class BlackListRepositoryTest {
	
	@Autowired
	private BlackListRepository blackListRepository;
	
	@BeforeEach
	private void setUp() {
		blackListRepository.save(DatosDummy.blackList01());
		blackListRepository.save(DatosDummy.blackList02());
	}
	
	@AfterEach
	private void terminar() {
		blackListRepository.deleteAll();
	}
	
	@Test
	@DisplayName("Buscando una ip baneada")
	void findBlackList() {
		//GWT
		//Given
		String ip = "1.2.3.4";
		//When
		Optional<BlackList> expected = blackListRepository.findByIpAndBanned(ip, true);
		//Then
		assertThat(expected.get()).isEqualTo(DatosDummy.blackList01());
	}
	
	@Test
	@DisplayName("Buscando una ip que no esta baneada")
	void findBlackListUnbanned() {
		//GWT
		//Given
		String ip = "1.2.3.4";
		//When
		Optional<BlackList> expected = blackListRepository.findByIpAndBanned(ip, false);
		//Then
		assertThat(expected.get()).isEqualTo(DatosDummy.blackList02());
	}
	
	@Test
	@DisplayName("Buscando una ip no existente")
	void missingBlackList() {
		//GWT
		//Given
		String ip = "1.2.3.5";
		//When
		Optional<BlackList> expected = blackListRepository.findByIp(ip);
		//Then
		assertThat(!expected.isPresent()).isTrue();
	}
}
