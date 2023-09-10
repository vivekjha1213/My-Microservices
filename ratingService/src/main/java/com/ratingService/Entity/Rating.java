package com.ratingService.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "User-Ratings")
public class Rating {

    @Id
    private String ratingsId= UUID.randomUUID().toString();;
    private String userId;
    private String hotelId;
    private int rating;
    private String feedback;
}
