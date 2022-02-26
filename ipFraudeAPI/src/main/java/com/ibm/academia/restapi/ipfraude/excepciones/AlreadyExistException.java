package com.ibm.academia.restapi.ipfraude.excepciones;

public class AlreadyExistException extends RuntimeException{

	public AlreadyExistException(String mensaje){
		super(mensaje);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -7565944047682329412L;
}
