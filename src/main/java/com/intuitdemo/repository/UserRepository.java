package com.intuitdemo.repository;

import java.util.List;

import com.intuitdemo.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUser, String> {

    MyUser findByUsername(String username);

    List<Username> getUsernameBy();

    interface Username {
        String getUsername();
    }
}