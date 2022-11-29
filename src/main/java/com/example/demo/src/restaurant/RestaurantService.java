package com.example.demo.src.restaurant;

import com.example.demo.config.BaseException;
import com.example.demo.src.restaurant.model.PostRestaurantReq;
import com.example.demo.src.restaurant.model.PostRestaurantRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;



@Service
public class RestaurantService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestaurantDao restaurantDao;

    private final RestaurantProvider restaurantProvider;

    private final JwtService jwtService;

    @Autowired
    public RestaurantService(RestaurantDao restaurantDao,RestaurantProvider restaurantProvider ,JwtService jwtService) {
        this.restaurantDao = restaurantDao;
        this.restaurantProvider = restaurantProvider;
        this.jwtService = jwtService;
    }

    public PostRestaurantRes createRestaurant(PostRestaurantReq postRestaurantReq) throws BaseException {

        try{
//            if(restaurantProvider.checkBusinessNum(postRestaurantReq.getCompanyRegistrationNumber()) == 1){
//                throw new BaseException(POST_RESTAURANT_EXISTS_BUSINESS_NUMBER);
//            }

            int restaurantIdx = restaurantDao.createRestaurant(postRestaurantReq);

            return new PostRestaurantRes(restaurantIdx);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyRestaurant(PostRestaurantReq restaurant, int restaurantId) throws BaseException {
        try{
            int result = restaurantDao.modifyRestaurant(restaurant ,restaurantId);

            if(result == 0){
                throw new BaseException(MODIFY_FAIL_RESTAURANT);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }
    public void deleteRestaurant(int restaurantId) throws BaseException{
        try{

            int result = restaurantDao.deleteRestaurant(restaurantId);


        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
