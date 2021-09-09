package com.app.domain.user.repository;

import com.app.domain.configs.repository.CrudRepository;
import com.app.domain.user.User;

import java.util.Optional;

/**
 * UserRepository interface
 * Provides CRUD methods to use with User
 *
 * @see CrudRepository
 * @see User
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}