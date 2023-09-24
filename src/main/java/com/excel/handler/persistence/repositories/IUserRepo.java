package com.excel.handler.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excel.handler.persistence.entities.UserEntity;

@Repository
public interface IUserRepo extends JpaRepository<UserEntity, Long> {

	public boolean existsByEmail(String email);
	
}
