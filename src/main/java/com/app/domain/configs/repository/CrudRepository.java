package com.app.domain.configs.repository;

import java.util.Optional;

/**
 * CrudRepository interface
 *
 * @param <T>  is generic object type injecting into repository
 * @param <ID> is object ID in database
 */
public interface CrudRepository<T, ID> {

    /**
     * This method adds T object to repository
     *
     * @param t is object to save
     * @return optional of added object
     */
    Optional<T> add(T t);

    /**
     * This method removes object from repository
     *
     * @param id is ID of object to remove
     * @return optional od removed object
     */
    Optional<T> delete(ID id);

    /**
     * This method returns matching object based on given ID
     *
     * @param id is object ID we are looking for
     * @return founded object or empty optional if object is not present
     */
    Optional<T> findById(ID id);
}