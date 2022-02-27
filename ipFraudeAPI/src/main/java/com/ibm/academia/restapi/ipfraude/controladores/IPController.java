package com.ibm.academia.restapi.ipfraude.controladores;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.academia.restapi.ipfraude.excepciones.BadRequestExternalApiException;
import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryDTO;
import com.ibm.academia.restapi.ipfraude.modelo.entidades.BlackList;
import com.ibm.academia.restapi.ipfraude.modelo.servicios.IPService;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
public class IPController {
	private final static Logger logger = LoggerFactory.getLogger(IPController.class);
	
	@Autowired
	private IPService ipService;
	
	/**
	 * Metodo para hacer ban a una IP
	 * @param ip
	 * @return Retorna la informacion guardada del ip y los datos del pais
	 * @author EMHH 23-02-22
	 */
	@PutMapping("/ban-ip/{ip}")
	public ResponseEntity<?> banIP(@PathVariable String ip)	{
		BlackList blackListSaved = null;
		try {
			blackListSaved = ipService.banIP(ip);
		} catch (FeignException.BadRequest e) {
			logger.warn(e.getMessage());
			throw new BadRequestExternalApiException(String.format("No hay datos para guardar sobre la IP %s",ip));
		} catch (BadRequestExternalApiException e) {
			logger.warn(e.getMessage());
			throw new BadRequestExternalApiException(e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage());
			
			Map<String, String> respuesta = new HashMap<String, String>();
			respuesta.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<BlackList>(blackListSaved, HttpStatus.OK);
	}
	
	/**
	 * Metodo para obtener la informacion de un país por la ip proporcionada
	 * @param ip
	 * @return Los datos del pais mediante un DTO
	 * @author EMHH 24-02-22
	 */
	@CircuitBreaker(name = "country-info", fallbackMethod = "metodoAlternativo")
	@TimeLimiter(name = "country-info")
	@GetMapping("/country-info/{ip}")
	public CompletableFuture<ResponseEntity<?>> getCountryByIP(@PathVariable String ip) {
		return CompletableFuture.supplyAsync( () -> new ResponseEntity<CountryDTO>(ipService.getCountryInfoByIP(ip), HttpStatus.OK));
	}
	
	/**
	 * Método alternativo para controlar los fallos de getCountryByIP
	 * @param e El error que se produce
	 * @return Retorna una respuesta en base al tipo de error
	 * @author EMHH - 23-02-2022
	 */
	public CompletableFuture<ResponseEntity<?>> metodoAlternativo(Throwable e) {
		logger.warn(e.getMessage());
		
		Map<String, Object> respuesta = new HashMap<>();
		respuesta.put("error", e.getMessage());
		
		if(e.getClass() == BadRequestExternalApiException.class)
			throw new BadRequestExternalApiException(e.getMessage());
		
		if(e instanceof FeignException.BadRequest)
			throw new BadRequestExternalApiException("Información no encontrada, considera usar otra IP.");
		
//		if(e.getClass() == Exception.class)
//		return new ResponseEntity<Map<String, String>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

		respuesta.put("data", new CountryDTO());
		return CompletableFuture.supplyAsync( () -> new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK));
	}
}