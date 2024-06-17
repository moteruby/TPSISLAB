package com.example.ERT.repositories;

import com.example.ERT.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    //User findByPhoneNumber(String phoneNumber);
    @Query("SELECT COUNT(u) FROM User u")
    long countUsers();
    User findByPhoneNumber(String phone);
}
