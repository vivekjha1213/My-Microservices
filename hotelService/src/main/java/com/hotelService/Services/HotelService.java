package com.hotelService.Services;

import com.hotelService.Entity.Hotel;

import java.util.List;

public interface HotelService {

    Hotel addAHotel(Hotel hotel);
    List<Hotel> getAllTheHotels();
    Hotel getHotelById(String hotelId);
    Hotel updateAHotel(String hotelId,Hotel hotelDetailsToBeUpdated);
    Hotel deleteAHotel(String id);


}
