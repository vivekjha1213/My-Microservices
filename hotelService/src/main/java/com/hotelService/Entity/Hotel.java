package com.hotelService.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="hotels")
public class Hotel {

    @Id()
    @Column(name = "hotel_id")
    private String hotelId= UUID.randomUUID().toString();
    private String name;
    private String location;
    private String about;


}
