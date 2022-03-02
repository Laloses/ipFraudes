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
	 * Method for baning an IP
	 * @param ip Value of the  IP to ban
	 * @return Returns the information of the country that belong to the IP and the id saved
	 * @throws BadRequestExternalApiException When an end-point fails
	 * @author EMHH 23-02-22
	 */
	@PutMapping("/ban-ip/{ip}")
	public ResponseEntity<?> banIP(@PathVariable String ip)	{
		BlackList blackListSaved = null;
		try {
			blackListSaved = ipService.banIP(ip);
		} catch (FeignException.BadRequest e) {
			logger.warn(e.getMessage());
			throw new BadRequestExternalApiException(String.format("There's no data about the IP, try later. %s",ip));
		} 
//		catch (Exception e) {
//			logger.warn(e.getMessage());
//			
//			Map<String, String> respuesta = new HashMap<String, String>();
//			respuesta.put("error", e.getMessage());
//			return new ResponseEntity<Map<String, String>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
		
		return new ResponseEntity<BlackList>(blackListSaved, HttpStatus.OK);
	}
	
	/**
	 * Method for baning an IP
	 * @param ip Value of the  IP to ban
	 * @return Returns the information of the country that belong to the IP and the id saved
	 * @throws NotFoundException If there is no IP saved with that value or is already unbaned
	 * @author EMHH 23-02-22
	 */
	@PutMapping("/unban-ip/{ip}")
	public ResponseEntity<?> unbanIP(@PathVariable String ip)	{
		BlackList blackList = ipService.unbanIP(ip);
		
		return new ResponseEntity<BlackList>(blackList, HttpStatus.OK);
	}
	
	/**
	 * Method to obtain the information of a country from the given IP
	 * @param ip Value of the IP to search
	 * @return Returns the country information in DTO format
	 * @throws BadRequestExternalApiException When an endpint fails
	 * @author EMHH 24-02-22
	 */
	@CircuitBreaker(name = "country-info", fallbackMethod = "methodAlternative")
	@TimeLimiter(name = "country-info")
	@GetMapping("/country-info/{ip}")
	public CompletableFuture<ResponseEntity<?>> getCountryByIP(@PathVariable String ip) {
		return CompletableFuture.supplyAsync( () -> new ResponseEntity<CountryDTO>(ipService.getCountryInfoByIP(ip), HttpStatus.OK));
	}
	
	/**
	 * Alternative method for controlling the exceptions of getCountryByIP
	 * @param e The exception
	 * @return Returns a response in base of the kind of error
	 * @author EMHH - 23-02-2022
	 */
	public CompletableFuture<ResponseEntity<?>> methodAlternative(Throwable e) {
		logger.warn(e.getMessage());
		
		Map<String, Object> respuesta = new HashMap<>();
		respuesta.put("error", e.getMessage());
		
		if(e.getClass() == BadRequestExternalApiException.class)
			throw new BadRequestExternalApiException(e.getMessage());
		
		if(e instanceof FeignException.BadRequest)
			throw new BadRequestExternalApiException("Information not found, consider using another IP. If the problem persists try later.");
		
//		if(e.getClass() == Exception.class)
//		return new ResponseEntity<Map<String, String>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

		respuesta.put("data", new CountryDTO());
		return CompletableFuture.supplyAsync( () -> new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK));
	}
}