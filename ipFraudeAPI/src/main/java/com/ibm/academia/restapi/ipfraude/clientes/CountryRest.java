package com.ibm.academia.restapi.ipfraude.clientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ibm.academia.restapi.ipfraude.excepciones.BadRequestExternalApiException;
import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryCoinDTO;

@FeignClient(name = "api-countrylayer", url = "${restcountry.url}")
public interface CountryRest{
	@GetMapping("/alpha/{code}")
	public CountryCoinDTO findCountryByCode3(
			@PathVariable("code") String code3
			) throws BadRequestExternalApiException;
}