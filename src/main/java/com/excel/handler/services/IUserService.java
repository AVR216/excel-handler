package com.excel.handler.services;

import java.util.List;

import com.excel.handler.persistence.entities.UserEntity;

public interface IUserService {

	UserEntity save(UserEntity entity);

	List<UserEntity> getAll();

}
