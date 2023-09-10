package com.hotelService.Controller;

import com.hotelService.Entity.Hotel;
import com.hotelService.Payload.Response;
import com.hotelService.Services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    HotelService hotelService;

    @PostMapping("/addingAHotel")
    public ResponseEntity<Response> addAHotel(@RequestBody Hotel hotel){
        try{
            Hotel hotelToBeAddedInDatabase=hotelService.addAHotel(hotel);
            return new ResponseEntity<>(new Response("Hotel is Added",hotelToBeAddedInDatabase),HttpStatus.OK);

        }catch (Exception exception){
            String errorMessage= exception.getMessage();
            Response response=Response.builder().message(errorMessage).build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getHotelById/{hotelId}")
    public ResponseEntity<Response> getTheHotelById(@PathVariable String hotelId){
        try{
            Hotel hotelFromDb=hotelService.getHotelById(hotelId);
            return new ResponseEntity<>(new Response("Hotel got From ",hotelFromDb),HttpStatus.OK);

        }catch (Exception exception){
            String errorMessage= exception.getMessage();
            Response response=Response.builder().message(errorMessage).build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllHotels")
    public ResponseEntity<Response> getAllHotelsFromTheSystem( ){
        try{
            List<Hotel> allHotelsInTheSystem=hotelService.getAllTheHotels();
            return new ResponseEntity<>(new Response("All the Hotels Present In The System ",allHotelsInTheSystem),HttpStatus.OK);

        }catch (Exception exception){
            String errorMessage= exception.getMessage();
            Response response=Response.builder().message(errorMessage).build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
