package com.excel.handler.controllers;

import static com.excel.handler.controllers.imp.UserValidationMessages.GENERAL_MESSAGE;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.excel.handler.persistence.entities.UserEntity;
import com.excel.handler.utils.LocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@Setter
@ToString
public class UserDto {
	@NotEmpty(message = GENERAL_MESSAGE)
	@NotBlank(message = GENERAL_MESSAGE)
	private String name;
	
	@NotEmpty(message = GENERAL_MESSAGE)
	@NotBlank(message = GENERAL_MESSAGE)
	private String email;
	
	@NotNull(message = GENERAL_MESSAGE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate birthDate;
	
	public static UserEntity dtoToEntity(UserDto dto) {
		return UserEntity.builder()
				.name(dto.getName())
				.email(dto.getEmail())
				.birthDate(dto.getBirthDate().atStartOfDay())
				.build();
	}
	
	public static UserDto entityToDto(UserEntity entity) {
		return UserDto.builder()
				.name(entity.getName())
				.email(entity.getEmail())
				.birthDate(entity.getBirthDate().toLocalDate())
				.build();
	}
}
