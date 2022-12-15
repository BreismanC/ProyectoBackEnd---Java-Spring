package com.dh.proyecto_BackEnd.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> tratamientoResourceNotFoundException(ResourceNotFoundException rnfe){
        if(rnfe.getMessage()!= null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<?> tratamientoResourceNotFoundException(BadRequestException bre){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());
    }
}
