package com.userService.Service;

import com.userService.Entity.Hotel;
import com.userService.Entity.Ratings;
import com.userService.Entity.Users;
import com.userService.Exception.ResouceNotFoundException;
import com.userService.ExternalService.CallingHotelService;
import com.userService.ExternalService.CallingRatingService;
import com.userService.Payload.Response;
import com.userService.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Value("${Rating-Service}")
    String ratingServiceUrl;

    @Value("${Hotel-Service}")
    String hotelServiceUrl;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CallingRatingService callingRatingService;

    @Autowired
    CallingHotelService callingHotelService;

    private final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Users saveOneUser(Users users) {
       return userRepository.save(users);

    }

    @Override
    public List<Users> getAllUsers() {
        List<Users> users=userRepository.findAll();

        List<Users> usersWithThierRatings = users.stream().map(eachUser->{

            List<Ratings> ratingsByThisUser=getTheRatingsByThisUser(eachUser.getUserId());
            eachUser.setRatingsGivenByThisUser(ratingsByThisUser);
            return eachUser;
        }).collect(Collectors.toList());
        return usersWithThierRatings;

    }

    private List<Ratings> getTheRatingsByThisUser(String userId) {


        ResponseEntity<Response> responseEntity=callingRatingService.getRatingForUserId(userId);

        List<Map<String,Object>> responseFromAPI=(List<Map<String,Object>>)responseEntity.getBody().getResult();

        List<Ratings> ratingsByThisUserId=giveTheRatingsListFromListOfMap(responseFromAPI);

        for(Ratings eachRating:ratingsByThisUserId){

            ResponseEntity<Response> responseFromHotelApi=callingHotelService.getHotelById(eachRating.getHotelId());

            Map<String,Object> hotelDetailsInMap=(Map<String,Object>)responseFromHotelApi.getBody().getResult();
            Hotel hotelDetails=new Hotel();
            hotelDetails.setHotelId((String) hotelDetailsInMap.get("hotelId"));
            hotelDetails.setName((String) hotelDetailsInMap.get("name"));
            hotelDetails.setLocation((String) hotelDetailsInMap.get("location"));
            hotelDetails.setAbout((String) hotelDetailsInMap.get("about"));
            eachRating.setHotel(hotelDetails);
        }
        return ratingsByThisUserId;
    }

    private List<Ratings> giveTheRatingsListFromListOfMap(List<Map<String, Object>> responseFromAPI) {
        List<Ratings> listOfRatings=new ArrayList<>();
        for(Map<String, Object> entry: responseFromAPI){

            Ratings ratings=new Ratings();
            ratings.setRatingsId((String) entry.get("ratingsId"));
            ratings.setUserId((String)entry.get("userId"));
            ratings.setHotelId((String)entry.get("hotelId"));
            ratings.setRating((int)entry.get("rating"));
            ratings.setFeedback((String)entry.get("feedback"));
            listOfRatings.add(ratings);

        }
        return listOfRatings;
    }

    @Override
    public Users giveAUser(String userId) {

        Users user = userRepository.findById(userId).orElseThrow(
                () -> new ResouceNotFoundException("User id is not found after Search : " + userId)
        );
        //Calling The Rating-Service MicroService To Know the Ratings Given by This User
        //Call This Url - www.localhost:8083/ratings/getAllTheRatingsByThisUserId/{userId}
        //This will return List<Ratings> -> we will attach on this userId

        String url = ratingServiceUrl + "getAllTheRatingsByThisUserId/" + userId;

        try{

        ResponseEntity<Response> responseEntity = restTemplate.getForEntity(url, Response.class);
        logger.info("{}", responseEntity);
        List<Map<String, Object>> listOfRatings = (List<Map<String, Object>>) responseEntity.getBody().getResult();
        logger.info("{}", listOfRatings);

        List<Ratings> newList = new ArrayList<>();
        for (Map<String, Object> eachRating : listOfRatings) {

            Ratings obj = new Ratings();
            eachRating.get("ratingsId");
            obj.setUserId((String) eachRating.get("userId"));
            obj.setFeedback((String) eachRating.get("feedback"));
            obj.setRatingsId((String) eachRating.get("ratingsId"));
            obj.setHotelId((String) eachRating.get("hotelId"));
            obj.setRating((int) eachRating.get("rating"));
            newList.add(obj);
        }
        logger.info("{}", newList);

        //You write Code to Know the which rating is for which hotel
        //api call to know the hotel from ratingsList
        //Set thr hotel to rating
        //return the rating

        List<Ratings> ratingsListWithHotels = newList.stream().map(rating -> {

            String urlForHotelApi = hotelServiceUrl + "/getHotelById/" + rating.getHotelId();
            ResponseEntity<Response> responseEntityForHotel = restTemplate.getForEntity(urlForHotelApi, Response.class);
            Hotel hotel = getTheHotelFromResponse(responseEntityForHotel);
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());
        user.setRatingsGivenByThisUser(ratingsListWithHotels);

        }
        catch(Exception exception){

            System.out.println(exception.getLocalizedMessage());
            System.out.println(exception.getMessage());
            System.out.println("Problem in Calling The API");
        }

        return user;
    }

    private Hotel getTheHotelFromResponse(ResponseEntity<Response> responseEntityForHotel) {
        Map<String,Object> map= (Map<String,Object>) responseEntityForHotel.getBody().getResult();
        Hotel hotel=new Hotel();
        hotel.setHotelId((String)map.get("hotelId"));
        hotel.setLocation((String)map.get("location"));
        hotel.setName((String)map.get("name"));
        hotel.setAbout((String)map.get("about"));
        return hotel;
    }

    @Override
    public Users deleteAUser(String userId) {
        return null;
    }

    @Override
    public Users updateAUser(Users updatedDetails, String Id) {
        return null;
    }
}

        /*

Backup -Codes
String token=httpServletRequest.getHeader("AUTHORIZATION").substring(7);
System.out.println(token);
List<String> roles=new ArrayList<>();
roles.add("ROLE_USER");
roles.add("SCOPE_email");
roles.add("SCOPE_offline_access");
roles.add("SCOPE_openid");
roles.add("SCOPE_profile");


HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_JSON);
headers.set("Authorization", "Bearer "+token);
headers.set("refreshToken",	"TXPW0hb1smZ26jMaSf-T65NzfnIZE6BOnJtOIp-VZ2E");
headers.set("expireAt","1677577079");
headers.set("userId","pranjalkatyayan03@gmail.com");

headers.setAccessControlAllowHeaders(roles);
headers.setAccessControlAllowCredentials(true);


HttpEntity<Object> entity = new HttpEntity<>(headers);
System.out.println(entity.getHeaders());
System.out.println(entity.getBody());
ResponseEntity<Response> entityForentity=restTemplate.getForEntity(url,Response.class);

ResponseEntity<Response> entityForObject = (ResponseEntity<Response>)restTemplate.postForObject(url,entity,Object.class);

*/
        /*

            MultiValueMap<String, String> bodyParamMap = new LinkedMultiValueMap<>();
            bodyParamMap.add("grant_type", "authorization_code");
            bodyParamMap.add("client_id", "0oa8dfjkmr9c1CRda5d7");
            bodyParamMap.add("client_secret", "9c4vDBDzB6Nf0RyB4-Cwo5WMi2fVX2IZ8tkSRlCc");
            List<Map<String, Object>> listOfRatings = (List<Map<String, Object>>) responseEntity.getBody().getResult();
            logger.info("{}",listOfRatings);


             */


