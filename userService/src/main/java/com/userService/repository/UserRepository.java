package com.userService.repository;


import com.userService.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,String> {

    //If You want any custom Repository we can implement Here
}
