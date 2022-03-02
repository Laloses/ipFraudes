package com.ibm.academia.restapi.ipfraude.modelo.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CountryRateDTO implements Serializable {
	private Double rate;
	
	private static final long serialVersionUID = -357287074753864138L;
}