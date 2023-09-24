package com.excel.handler.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.multipart.MultipartFile;

import com.excel.handler.persistence.entities.UserEntity;

public interface IUserExcelService {

	public void processFile(File file) throws IOException;
	
	public File convertMultipartToFile(MultipartFile multipartFile) throws IllegalStateException, IOException;
	
	UserEntity buildEntity(Row row);
	
	public List<Row> getRowsWithErrors();
	
	public int getNumberOfErrors();
	
	public void resetValues();
}
