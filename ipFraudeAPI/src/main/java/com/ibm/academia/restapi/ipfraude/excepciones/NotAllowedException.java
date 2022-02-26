package com.ibm.academia.restapi.ipfraude.excepciones;

public class NotAllowedException extends RuntimeException{

	public NotAllowedException(String mensaje){
		super(mensaje);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 5839647968836668786L;
}
