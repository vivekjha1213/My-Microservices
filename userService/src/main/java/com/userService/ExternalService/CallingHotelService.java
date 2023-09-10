package com.userService.ExternalService;

import com.userService.Entity.Hotel;
import com.userService.Payload.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="HOTEL-SERVICE")
public interface CallingHotelService {

    @GetMapping("hotels/getHotelById/{hotelId}")
    ResponseEntity<Response> getHotelById(@PathVariable String hotelId);
}
