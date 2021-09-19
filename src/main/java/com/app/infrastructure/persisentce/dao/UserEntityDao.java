package com.app.infrastructure.persisentce.dao;

import com.app.infrastructure.persisentce.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityDao extends JpaRepository<UserEntity, Long> {
}