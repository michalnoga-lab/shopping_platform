package com.app.jpa;

import com.app.model.User;
import com.app.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("save user to DB")
    public void test1() {
        User user1 = User.builder().name("Name 1").build();
        User user2 = User.builder().name("Name 2").build();
        User user3 = User.builder().name("Name 3").build();

        testEntityManager.persist(user1);
        testEntityManager.persist(user2);
        testEntityManager.persist(user3);
        testEntityManager.flush();

        Assertions.assertEquals(3, userRepository.findAll().size());
    }

    @Test
    @DisplayName("remove user from DB")
    public void test2() {

        User user1 = User.builder().name("Name 1").build();
        User user2 = User.builder().name("Name 2").build();
        User user3 = User.builder().name("Name 3").build();

        testEntityManager.persist(user1);
        testEntityManager.persist(user2);
        testEntityManager.persist(user3);

        Assertions.assertEquals(3, userRepository.findAll().size());

        testEntityManager.remove(user1);
        Assertions.assertEquals(2, userRepository.findAll().size());

        testEntityManager.remove(user2);
        Assertions.assertEquals(1, userRepository.findAll().size());

        testEntityManager.remove(user3);
        Assertions.assertEquals(0, userRepository.findAll().size());
    }
}