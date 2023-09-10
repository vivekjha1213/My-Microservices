package com.hotelService.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Table;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="PostOffice")
public class PostOffice {

    String Name;
    String Desciption;
    String BranchType;
    String DeliveryStatus;
    String Circle;
    String District;
    String Division;
    String Region;
    String State;
    String Country;

}
