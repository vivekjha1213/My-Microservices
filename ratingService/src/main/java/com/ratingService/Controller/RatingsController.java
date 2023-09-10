package com.ratingService.Controller;

import com.ratingService.Entity.Rating;
import com.ratingService.Payload.Response;
import com.ratingService.Services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingsController {

    @Autowired
    RatingService ratingService;

    @PreAuthorize("hasAuthority('SCOPE_email')")
    @PostMapping("/addARatings")
    public ResponseEntity<Response> createARating(@RequestBody Rating rating){
        try{
            Rating ratingObjCreated=ratingService.createARating(rating);
            return new ResponseEntity<>(new Response("Rating Is created",ratingObjCreated),HttpStatus.OK);
        }catch (Exception exception){
            String errorMessage=exception.getMessage();
            Response response=Response.builder().message(errorMessage).build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/GetAllRatingsPresentInSystem")
    public ResponseEntity<Response> getAllRatingsPresentInSystem(){
        try{
            List<Rating> allTheRatingInSystem=ratingService.getAllRatings();
            return new ResponseEntity<>(new Response("Rating Present In The System",allTheRatingInSystem),HttpStatus.OK);
        }catch (Exception exception){
            String errorMessage=exception.getMessage();
            Response response=Response.builder().message(errorMessage).build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllTheRatingsByThisUserId/{userId}")
    public ResponseEntity<Response> getAllRatingsByUserId(@PathVariable String userId){
        try{
            List<Rating> ratingListByThisUserId=ratingService.getRatingsByUserId(userId);
            return new ResponseEntity<>(new Response("Rating By This User Id",ratingListByThisUserId),HttpStatus.OK);
        }catch (Exception exception){
            String errorMessage=exception.getMessage();
            Response response=Response.builder().message(errorMessage).build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllTheRatingsForThisHotel/{hotelId}")
    public ResponseEntity<Response> getAllTheRatingsForThisHotel(@PathVariable String hotelId){
        try{
            List<Rating> ratingListForThisHotelId=ratingService.getRatingsByHotelId(hotelId);
            return new ResponseEntity<>(new Response("All Ratings for This Hotel ",ratingListForThisHotelId),HttpStatus.OK);
        }catch (Exception exception){
            String errorMessage=exception.getMessage();
            Response response=Response.builder().message(errorMessage).build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
