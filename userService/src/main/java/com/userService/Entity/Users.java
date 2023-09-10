package com.userService.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name ="Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id()
    @Column(name = "userID")
    private String userId= UUID.randomUUID().toString();

    @Column(name = "Name" ,length = 20)
    private String name;

    @Column(name = "Email" )
    private String email;

    @Column(name = "Mobile" , length = 10)
    private String mobileNo;

    @Column(name = "About")
    private String about;

    @Transient
    private List<Ratings> ratingsGivenByThisUser;

    //You can add Other Details for Users

}
