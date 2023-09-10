package com.hotelService.Controller;

import com.hotelService.Entity.Staff;
import com.hotelService.Payload.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @PostMapping("/addAStaff")
    public ResponseEntity<Response> addAStaff(@RequestBody Staff staff){
        try{
            Staff staffCreated=Staff.builder().staffName("Ram").build();
            return new ResponseEntity<>(new Response("New Staff is Created",staffCreated),HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(new Response("Staff could not be created",null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
