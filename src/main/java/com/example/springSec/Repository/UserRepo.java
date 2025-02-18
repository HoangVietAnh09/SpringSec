package com.example.springSec.Repository;

import com.example.springSec.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    User findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);


    Optional<User> findByUsername(String username);


    @Query(value = "select * from users where username = :username", nativeQuery = true)
    List<User> findByUsernameVuln(String username);
}
