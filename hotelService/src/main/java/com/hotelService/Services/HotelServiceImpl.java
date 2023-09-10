package com.hotelService.Services;

import com.hotelService.Entity.Hotel;
import com.hotelService.Exception.ResouceNotFoundException;
import com.hotelService.Repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService{

    @Autowired
   HotelRepository hotelRepository;

    @Override
    public Hotel addAHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllTheHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotelById(String hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(
                ()-> new ResouceNotFoundException("Hotel is  Not Present with this Hotel Id "+ hotelId)
        );
    }

    @Override
    public Hotel updateAHotel(String hotelId, Hotel hotelDetailsToBeUpdated) {
        return null;
    }

    @Override
    public Hotel deleteAHotel(String id) {
        return null;
    }
}
