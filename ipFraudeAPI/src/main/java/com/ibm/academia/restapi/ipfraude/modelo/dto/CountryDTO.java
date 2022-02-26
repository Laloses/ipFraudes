package com.ibm.academia.restapi.ipfraude.modelo.dto;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class CountryDTO implements Serializable {
	private String nombre;
	private String ISO;
	private String moneda;
	@JsonProperty("cotizacion_actual")
	private Double cotizacion;
	@JsonProperty("cotizacion_moneda")
	private String cotizacionMoneda = "EUR"; //Inicializada asi por el API que se usa
	/**
	 * 
	 */
	private static final long serialVersionUID = -8869578670292445720L;
	
}