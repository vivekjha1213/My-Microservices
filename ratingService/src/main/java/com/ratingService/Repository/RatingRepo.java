package com.ratingService.Repository;

import com.ratingService.Entity.Rating;

import java.util.List;

public interface RatingRepo {

    Rating save(Rating rating);
    List<Rating> findAll();
    List<Rating> findByUserId(String userId);
    List<Rating> findByHotelId(String hotelId);
}
