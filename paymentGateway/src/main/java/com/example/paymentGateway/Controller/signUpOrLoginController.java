package com.example.paymentGateway.Controller;

import com.example.paymentGateway.Model.User;
import com.example.paymentGateway.Service.AppService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/myApp")
public class signUpOrLoginController {

    Logger logger= LoggerFactory.getLogger(signUpOrLoginController.class);

    @Autowired
    AppService appService;

    @PostMapping("/api/signUpForNewUsers")
    public ResponseEntity<Object> signUpForNewUsers(@RequestBody User user){

        try{
            long t1=System.currentTimeMillis();
            if(user == null){
                return new ResponseEntity<>("Invalid Input", HttpStatus.BAD_REQUEST);
            }
            ResponseEntity<Object> responseEntity = appService.signUpForNewUsers(user);
            logger.info("controller timing : {} : {}", (System.currentTimeMillis() - t1), "signUpForNewUsers api");
            return responseEntity;

        }catch (Exception exception){
            exception.printStackTrace();
            String error = exception.getMessage();
            logger.error("Error in API- /signUpForNewUsers"  + error);
            return new ResponseEntity(new Response(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/api/login")
    public ResponseEntity<Object> loginForUser(@RequestBody HashMap<String,Object> mobileNoAndPassword){

        try{
            long t1=System.currentTimeMillis();
            if(mobileNoAndPassword == null){
                return new ResponseEntity<>("Invalid Input", HttpStatus.BAD_REQUEST);
            }
            String mobileNo= (String) mobileNoAndPassword.get("mobile");
            String password= (String) mobileNoAndPassword.get("password");
            ResponseEntity<Object> responseEntity = appService.loginForMobileNo(mobileNo,password);
            return responseEntity;

        }catch (Exception exception){
            exception.printStackTrace();
            String error = exception.getMessage();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/api/viewTheProfile/{existingMobileNo}")
    public ResponseEntity<Object> viewTheProfile(@PathVariable String existingMobileNo){

        try{
            long t1=System.currentTimeMillis();
            if(existingMobileNo == null || existingMobileNo.length()>10){
                return new ResponseEntity<>("Invalid Input", HttpStatus.BAD_REQUEST);
            }
            ResponseEntity<Object> responseEntity = appService.viewTheProfile(existingMobileNo);
            return responseEntity;

        }catch (Exception exception){
            exception.printStackTrace();
            String error = exception.getMessage();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/api/updateTheProfile/{existingMobileNo}")
    public ResponseEntity<Object> updateTheProfile(@RequestBody User updatedData ,@PathVariable String existingMobileNo){

        try{
            long t1=System.currentTimeMillis();
            if(existingMobileNo == null || existingMobileNo.length()>10){
                return new ResponseEntity<>("Invalid Input", HttpStatus.BAD_REQUEST);
            }
            ResponseEntity<Object> responseEntity = appService.updateTheProfileOfExistingUser(updatedData,existingMobileNo);
            return responseEntity;

        }catch (Exception exception){
            exception.printStackTrace();
            String error = exception.getMessage();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
