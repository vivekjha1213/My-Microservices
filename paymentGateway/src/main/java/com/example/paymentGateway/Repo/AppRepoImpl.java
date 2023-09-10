package com.example.paymentGateway.Repo;

import com.example.paymentGateway.Model.User;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppRepoImpl implements AppRepo{

    @Autowired
    MongoTemplate mongoTemplate;

    public boolean checkIfThisMobileNoExists(String mobile) {
        Query query=new Query();
        query.addCriteria(Criteria.where("mobileNo").is(mobile));
        List<User> list=mongoTemplate.find(query, User.class,"Users");
        if(list.size()>0){
            return true;
        }
        return false;
    }

    public User saveTheDataOfNewUserInDb(User user) {

       User userSaved= mongoTemplate.save(user,"Users");
       return userSaved;
    }

    @Override
    public User loginForMobile(String mobileNo, String password) {

        Query query=new Query();
        query.addCriteria(Criteria.where("mobileNo").is(mobileNo));
        query.addCriteria(Criteria.where("password").is(password));
        User user=mongoTemplate.findOne(query,User.class,"Users");
        String passwordFromDataBase=user.getPassword();
        if(!password.equals(passwordFromDataBase)){
            return null;
        }
        return user;

    }

    @Override
    public UpdateResult updateTheProfileForMobileNo(Update update, Query query) {
        UpdateResult updateResult=mongoTemplate.updateFirst(query,update,User.class,"Users");
        return updateResult;
      }

    @Override
    public User getTheUserFromDb(String existingMobileNo) {

        Query query=new Query();
        query.addCriteria(Criteria.where("mobileNo").is(existingMobileNo));
        User user=mongoTemplate.findOne(query,User.class,"Users");
        return user;
    }
}
