package com.hotelService.Payload;

import com.hotelService.Entity.PostOffice;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseForPostOffice {

    String Message;
    String Status;
    PostOffice[] postOffices;

}
