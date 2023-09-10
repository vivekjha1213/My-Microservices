package com.example.paymentGateway.Service;

import com.example.paymentGateway.Model.User;
import org.springframework.http.ResponseEntity;

public interface AppService {

    ResponseEntity<Object> signUpForNewUsers(User user);

    ResponseEntity<Object> loginForMobileNo(String mobileNo, String password);

    ResponseEntity<Object> updateTheProfileOfExistingUser(User updatedData, String existingMobileNo);

    ResponseEntity<Object> viewTheProfile(String existingMobileNo);
}
