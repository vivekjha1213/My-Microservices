package com.hotelService.Controller;

import com.hotelService.Payload.ResponseForPostOffice;
import com.hotelService.Services.PinCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pincode")
public class PinCodeController {

    @Autowired
    PinCodeService pinService;

    @GetMapping("/getThePostOffices/{pinCode}")
    public ResponseEntity<ResponseForPostOffice> getThePostOfficeFromPinCode(@PathVariable String pinCode){
        try{
            ResponseForPostOffice response=pinService.getThePostOffices(pinCode);
            return new ResponseEntity<>(response,HttpStatus.OK);

        }catch(Exception ex){
            String msg=ex.getMessage();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
