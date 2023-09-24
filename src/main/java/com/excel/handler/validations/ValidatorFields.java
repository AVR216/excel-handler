package com.excel.handler.validations;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.excel.handler.exceptions.UserGeneralException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidatorFields<K> {

	private final Validator validator;

	public <T> T validate(T object) {
		Set<ConstraintViolation<T>> errors = validator.validate(object);
		if (errors.isEmpty())
			return object;
		else {
			log.info(errors.toString());
			String message = errors.stream().map(err -> err.getMessage() + err.getPropertyPath())
					.collect(Collectors.joining(", "));
			var firstMessage = message.split(",")[0];
			throw new UserGeneralException(HttpStatus.BAD_REQUEST.value(), firstMessage);
		}
	}

	public void validateList(List<K> list) {
		list.forEach(this::validate);
	}

	public <T> boolean hasError(T object) {
		var errors = validator.validate(object);
		return errors.isEmpty();
	}
}