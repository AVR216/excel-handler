package com.excel.handler.persistence.entities;

import java.time.LocalDateTime;

import static com.excel.handler.controllers.imp.UserValidationMessages.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = GENERAL_MESSAGE)
	@NotBlank(message = GENERAL_MESSAGE)
	private String name;
	
	@NotBlank(message = GENERAL_MESSAGE)
	@NotEmpty(message = GENERAL_MESSAGE)
	private String email;

	@NotNull(message = GENERAL_MESSAGE)
	private LocalDateTime birthDate;
}
