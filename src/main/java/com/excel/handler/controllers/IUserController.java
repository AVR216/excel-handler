package com.excel.handler.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IUserController {

	ResponseEntity<Object> save(UserDto dto);

	ResponseEntity<Object> getAll();
	
	ResponseEntity<Object> bulkUpload(MultipartFile file) throws IOException;

}
