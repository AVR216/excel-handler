package com.excel.handler.services.imp;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.excel.handler.exceptions.UserGeneralException;
import com.excel.handler.persistence.entities.UserEntity;
import com.excel.handler.persistence.repositories.IUserRepo;
import com.excel.handler.services.IUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements IUserService {

	private final IUserRepo userRepo;

	@Override
	public UserEntity save(UserEntity entity) {

		if (this.userRepo.existsByEmail(entity.getEmail())) {
			throw new UserGeneralException(HttpStatus.CONFLICT.value(), "Email is already registered.");
		}

		var entitySaved = this.userRepo.save(entity);
		log.info(".: Entity saved :.");
		return entitySaved;
	}

	@Override
	public List<UserEntity> getAll() {
		var entityList = this.userRepo.findAll();
		log.info(".: Entity list got :.");
		return entityList;
	}

}
