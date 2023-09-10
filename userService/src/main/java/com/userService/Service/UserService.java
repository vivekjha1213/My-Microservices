package com.userService.Service;

import com.userService.Entity.Users;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface UserService {

    Users saveOneUser(Users users);

    List<Users> getAllUsers();

    Users giveAUser(String userId);

    Users deleteAUser(String userId);

    Users updateAUser(Users updatedDetails,String Id);


}
