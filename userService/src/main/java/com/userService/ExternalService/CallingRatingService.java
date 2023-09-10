package com.userService.ExternalService;

import com.userService.Entity.Ratings;
import com.userService.Payload.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="RATING-SERVICE")
public interface CallingRatingService {


    @GetMapping("/ratings/getAllTheRatingsByThisUserId/{userId}")
    public ResponseEntity<Response> getRatingForUserId(@PathVariable String userId);




}
