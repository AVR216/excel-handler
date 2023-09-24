package com.excel.handler.controllers.imp;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.excel.handler.controllers.IUserController;
import com.excel.handler.controllers.UserDto;
import com.excel.handler.services.IUserExcelService;
import com.excel.handler.services.IUserService;
import com.excel.handler.validations.ValidatorFields;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController implements IUserController {

	private final IUserService userService;
	private final ValidatorFields<UserDto> validator;
	private final IUserExcelService excelService;

	@PostMapping(path = { "/" })
	@Override
	public ResponseEntity<Object> save(@RequestBody UserDto dto) {

		this.validator.validate(dto);

		return ResponseEntity.ok(UserDto.entityToDto(this.userService.save(UserDto.dtoToEntity(dto))));
	}

	@GetMapping(path = { "/" })
	@Override
	public ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(this.userService.getAll().stream().map(UserDto::entityToDto));
	}

	@PostMapping(path = { "/upload" })
	@Override
	public ResponseEntity<Object> bulkUpload(@RequestParam(name = "file") MultipartFile file) throws IOException {

		this.excelService.processFile(this.excelService.convertMultipartToFile(file));

		log.info("Existen {} registro(s) con errores", this.excelService.getNumberOfErrors());

		this.excelService.getRowsWithErrors().forEach(row -> log.error("Fila: {}, data: {}", row.getRowNum() + 1,
				UserDto.entityToDto(this.excelService.buildEntity(row))));

		this.excelService.resetValues();

		return ResponseEntity.ok().build();
	}
}
