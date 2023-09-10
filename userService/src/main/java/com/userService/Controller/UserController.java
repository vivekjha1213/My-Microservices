package com.userService.Controller;

import com.userService.Entity.Users;
import com.userService.Payload.ApiResponse;
import com.userService.Payload.Response;
import com.userService.Service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.Getter;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    Logger logger;

    //create A User
    @PostMapping("/createANewUser")
    public ResponseEntity<Users> createAUser(@RequestBody Users users){
        try{
            Users userCreated=userService.saveOneUser(users);
            return new ResponseEntity<>(userCreated, HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //get A singleUser
    @GetMapping("/getOneUser/{userId}")
    @CircuitBreaker(name= "ratingHotelBreaker" , fallbackMethod = "ratingAndHotelIsDown" )
    public ResponseEntity<Response> getAUser(@PathVariable String userId){
        try {

            Users usersFetchedFromDB=userService.giveAUser(userId);
            return new ResponseEntity<>(new Response("The User is Created",usersFetchedFromDB),HttpStatus.OK);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            System.out.println(exception);
            return new ResponseEntity<>(new Response("There is an error ",null),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //Creating Fall Back Method for Circuit Breaker - RatingAndHotelIsDown
    public ResponseEntity<Response> ratingAndHotelIsDown(String userId, Exception exception){
        System.out.println("Circuit Breaker is down"+ userId);
        logger.info("The Circuit is broken",exception.getMessage() + userId);
        return new ResponseEntity<>(new Response("The Circuit is Broken -Rating Service is Down",null),HttpStatus.OK);
    }

    //Get All users
    @GetMapping("/getAllTheUsers")
    public ResponseEntity<List<Users>> getAllUsers(){
        try{
            List<Users> allUsersInDataBases=userService.getAllUsers();
            return new ResponseEntity<>(allUsersInDataBases,HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
