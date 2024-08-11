package com.SpringBoot.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<?> handlingRuntimeException(RuntimeException re){
		return ResponseEntity.badRequest().body(re.getMessage());
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<?> handlingValidStringException(MethodArgumentNotValidException me){
		List<FieldError> listFieldErr = me.getFieldErrors();
		List<String> listMessage = new ArrayList<String>();
		for (FieldError err : listFieldErr) {
			listMessage.add(err.getDefaultMessage());
		}
		return ResponseEntity.badRequest().body(listMessage);
	}
}
