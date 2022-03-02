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
	private String name;
	private String ISO;
	private String coin;
	@JsonProperty("actual_rate")
	private Double rate;
	@JsonProperty("rate_coin")
	private String rateCoin = "EUR"; //Initialize because the free API has that value

	private static final long serialVersionUID = -8869578670292445720L;
}