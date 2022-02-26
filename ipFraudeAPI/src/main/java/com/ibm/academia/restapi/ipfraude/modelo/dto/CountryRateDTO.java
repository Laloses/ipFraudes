package com.ibm.academia.restapi.ipfraude.modelo.dto;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CountryRateDTO implements Serializable {
	private Double cotizacion;
	/**
	 * 
	 */
	private static final long serialVersionUID = -357287074753864138L;
}