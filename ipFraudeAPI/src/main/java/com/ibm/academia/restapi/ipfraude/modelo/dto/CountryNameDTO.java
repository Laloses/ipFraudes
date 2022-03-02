package com.ibm.academia.restapi.ipfraude.modelo.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CountryNameDTO implements Serializable {
	@JsonProperty("countryCode3")
	private String ISO;
	@JsonProperty("countryName")
	private String name;
	
	private static final long serialVersionUID = 2457491903583350154L;
}