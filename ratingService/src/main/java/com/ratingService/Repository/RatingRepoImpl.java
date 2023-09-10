package com.ratingService.Repository;

import com.ratingService.Entity.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class RatingRepoImpl implements RatingRepo{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Rating save(Rating rating) {
        return mongoTemplate.save(rating,"Ratings");
    }

    @Override
    public List<Rating> findAll() {
        return mongoTemplate.findAll(Rating.class,"Ratings");
    }

    @Override
    public List<Rating> findByUserId(String userId) {

        Query query=new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query,Rating.class,"Ratings");
    }

    @Override
    public List<Rating> findByHotelId(String hotelId) {

        Query query=new Query();
        query.addCriteria(Criteria.where("hotelId").is(hotelId));
        return mongoTemplate.find(query,Rating.class,"Ratings");

    }
}
