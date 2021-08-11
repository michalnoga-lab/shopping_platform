package com.app.domain.user.repository;

import com.app.domain.configs.repository.CrudRepository;
import com.app.domain.user.User;

/**
 * UserRepository interface
 * Provides CRUD methods to use with User
 *
 * @see CrudRepository
 */
public interface UserRepository extends CrudRepository<User, Long> {
}