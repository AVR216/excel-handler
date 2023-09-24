package com.excel.handler.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;

import com.excel.handler.exceptions.UserGeneralException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LocalDateDeserializer() {
		super(LocalDate.class);
	}

	@Override
	public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		try {
			return LocalDate.parse(parser.readValueAs(String.class));
		} catch (DateTimeParseException e) {
			throw new UserGeneralException(HttpStatus.BAD_REQUEST.value(), "Date format invalid, must be yyyy-MM-dd");
		}
	}

}
