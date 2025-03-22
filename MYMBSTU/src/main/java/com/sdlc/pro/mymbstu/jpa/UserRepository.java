package com.sdlc.pro.mymbstu.jpa;

import com.sdlc.pro.mymbstu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Find a user by their username
    Optional<User> findByUsername(String username);

    // Find a user by their email
    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<User> findByEmail(@Param("email") String email);
    //Optional<User> findByEmail(String email);

    // Find a user by their ID (assuming ID is unique for each user)
    @Query("SELECT u FROM User u WHERE LOWER(u.id) = LOWER(:id)")
    Optional<User> findById(@Param("id") String id);
    //Optional<User> findById(String id);

    // Count the number of users
    long count(); // This method is inherited from JpaRepository
}
