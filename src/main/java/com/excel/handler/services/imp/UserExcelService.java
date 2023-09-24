package com.excel.handler.services.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.excel.handler.persistence.entities.UserEntity;
import com.excel.handler.persistence.repositories.IUserRepo;
import com.excel.handler.services.IUserExcelService;
import com.excel.handler.validations.ValidatorFields;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserExcelService implements IUserExcelService {

	private final IUserRepo userRepo;

	private final ValidatorFields<UserEntity> validatorFields;

	private List<Row> rowsWithErrors = new ArrayList<>();

	@Value("${upload.directory}")
	private String uploadDirectory;

	@Async
	@Transactional
	@Override
	public void processFile(File file) throws IOException {

		try (FileInputStream fis = new FileInputStream(file); Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> iteratorRow = sheet.iterator();

			var entities = new ArrayList<UserEntity>();

			while (iteratorRow.hasNext()) {

				Row row = iteratorRow.next();
				if (row.getRowNum() == 0) { // ignore the header
					continue;
				}

				var user = buildEntity(row);

				if (!this.validatorFields.hasError(user)) {
					rowsWithErrors.add(row);
				} else {
					entities.add(user);
				}
			}
			this.userRepo.saveAll(entities);
		}
	}

	@Override
	public File convertMultipartToFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
		var fileName = multipartFile.getOriginalFilename();
		var file = new File(uploadDirectory, fileName);
		multipartFile.transferTo(file);
		return file;
	}

	@Override
	public UserEntity buildEntity(Row row) {
		return UserEntity.builder().name(row.getCell(0) == null ? null : row.getCell(0).getStringCellValue())
				.email(row.getCell(1) == null ? null : row.getCell(1).getStringCellValue())
				.birthDate(row.getCell(2) == null ? null : row.getCell(2).getLocalDateTimeCellValue()).build();
	}

	public List<Row> getRowsWithErrors() {
		return rowsWithErrors;
	}

	public int getNumberOfErrors() {
		return rowsWithErrors.size();
	}

	@Override
	public void resetValues() {
		this.rowsWithErrors = new ArrayList<>();
	}
}
