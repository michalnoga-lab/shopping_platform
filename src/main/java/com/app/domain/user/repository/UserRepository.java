package com.app.domain.user.repository;

import com.app.domain.repository.CrudRepository;
import com.app.domain.user.User;

/**
 * UserRepository interface
 * Provides all methods from CrudRepository to use with User
 *
 * @see CrudRepository
 */
public interface UserRepository extends CrudRepository<User, Long> {
}