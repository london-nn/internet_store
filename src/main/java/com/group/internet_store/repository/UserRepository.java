package com.group.internet_store.repository;

import com.group.internet_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getOptionalUserByUsername(String username);

    User getUserByUsername(String username);

    boolean existsUserByUsername(String username);
}
