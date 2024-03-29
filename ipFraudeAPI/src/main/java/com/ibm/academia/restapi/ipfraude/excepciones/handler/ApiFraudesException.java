package com.ibm.academia.restapi.ipfraude.excepciones.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ibm.academia.restapi.ipfraude.excepciones.AlreadyExistException;
import com.ibm.academia.restapi.ipfraude.excepciones.BadRequestExternalApiException;
import com.ibm.academia.restapi.ipfraude.excepciones.NotAllowedException;
import com.ibm.academia.restapi.ipfraude.excepciones.NotFoundException;

@ControllerAdvice
public class ApiFraudesException {
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> notExistException(NotFoundException exception) {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("error", exception.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(value = BadRequestExternalApiException.class)
    public ResponseEntity<Object> wrongAnswerException(BadRequestExternalApiException exception) {
    	Map<String, Object> respuesta = new HashMap<String, Object>();
    	respuesta.put("error", exception.getMessage());
    	return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = NotAllowedException.class)
    public ResponseEntity<Object> dontHavePermissionException(NotAllowedException exception) {
    	Map<String, Object> respuesta = new HashMap<String, Object>();
    	respuesta.put("error", exception.getMessage());
    	return new ResponseEntity<>(respuesta, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = AlreadyExistException.class)
    public ResponseEntity<Object> alreadyExistsException(AlreadyExistException exception) {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("error", exception.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
}
