package com.ibm.academia.restapi.ipfraude.excepciones;

public class BadRequestExternalApiException extends RuntimeException{

	public BadRequestExternalApiException(String mensaje){
		super(mensaje);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4997803544320667027L;
}
