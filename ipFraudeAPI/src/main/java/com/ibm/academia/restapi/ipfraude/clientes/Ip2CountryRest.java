package com.ibm.academia.restapi.ipfraude.clientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ibm.academia.restapi.ipfraude.excepciones.BadRequestExternalApiException;
import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryNameDTO;

@FeignClient(name = "api-ip2country", url = "${ip2country.url}" )
public interface Ip2CountryRest{
	@GetMapping("/ip?{ip}")
	public CountryNameDTO findCountryByIP(@PathVariable String ip) throws BadRequestExternalApiException;
}