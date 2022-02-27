package com.ibm.academia.restapi.ipfraude.clientes;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ibm.academia.restapi.ipfraude.excepciones.BadRequestExternalApiException;

@FeignClient(name = "api-fixer", url = "${fixer.url}")
public interface FixerRest{
	@GetMapping("/latest")
	public Map<String,Object> getAllBaseDefault(@RequestParam(name = "access_key") String apiKey) throws BadRequestExternalApiException;
}