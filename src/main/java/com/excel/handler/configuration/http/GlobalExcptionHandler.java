package com.excel.handler.configuration.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.excel.handler.exceptions.UserGeneralException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExcptionHandler {

	@ResponseBody
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleJsonException(HttpMessageNotReadableException ex) {

		log.error(ex.getMessage(), ex);

		return ResponseEntity.badRequest().body("Invalid date format, must be yyyy-MM-dd");
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception ex) {

		log.error(ex.getMessage(), ex);

		if (ex instanceof UserGeneralException userGeneralException) {
			return new ResponseEntity<>(userGeneralException.getMessage(),
					HttpStatus.valueOf(userGeneralException.getCode()));
		}

		return ResponseEntity.internalServerError().build();
	}
}
