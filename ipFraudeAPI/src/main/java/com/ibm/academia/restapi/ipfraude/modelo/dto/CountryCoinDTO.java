package com.ibm.academia.restapi.ipfraude.modelo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CountryCoinDTO implements Serializable {
//	@JsonProperty("status")
//	private String error;
	private String coin;
	
	@JsonProperty("currencies")
	private void unpackCurrencies(List<Map<String, Object>> currencies) {
		coin = (String)currencies.get(0).get("code");
	}
	
	private static final long serialVersionUID = 2366095346076959598L;
}