package com.hotelService.Entity;

import lombok.*;

import javax.persistence.Table;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="Hotel-Staffs")
public class Staff {

    private String staffId= UUID.randomUUID().toString();
    private String staffName;
    private String designation;
    private String mobileNo;
    private String emailAddress;
    private String address;
}
