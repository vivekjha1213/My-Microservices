package com.ratingService.Services;

import com.ratingService.Entity.Rating;

import java.util.List;

public interface RatingService {

    Rating createARating(Rating rating);
    List<Rating> getAllRatings();
    List<Rating> getRatingsByUserId(String userId);
    List<Rating> getRatingsByHotelId(String hotelId);




}

