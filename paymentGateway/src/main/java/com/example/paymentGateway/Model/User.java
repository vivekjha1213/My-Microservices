package com.example.paymentGateway.Model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "Users")
public class User {


    String mobileNo;
    String emailId;
    String password;
    String firstName;
    String lastName;
    String address;
    String pincode;
    String dOB;
    String panCard;


}
