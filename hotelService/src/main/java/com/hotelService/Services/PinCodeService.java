package com.hotelService.Services;

import com.hotelService.Payload.ResponseForPostOffice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PinCodeService {

    @Autowired
    RestTemplate restTemplate;

    String url="https://api.postalpincode.in/pincode/";

    public ResponseForPostOffice getThePostOffices(String pinCode) {

        url=url+pinCode;
        ResponseEntity<Object> responseEntity
                =restTemplate.getForEntity(url,Object.class);
        ResponseForPostOffice[] responseList=(ResponseForPostOffice[])responseEntity.getBody();
        ResponseForPostOffice response=responseList[0];
        return response;
    }
}