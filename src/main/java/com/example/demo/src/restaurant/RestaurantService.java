package com.example.demo.src.restaurant;

import com.example.demo.config.BaseException;
import com.example.demo.src.restaurant.model.GetRestaurantRes;
import com.example.demo.src.restaurant.model.PostRestaurantReq;
import com.example.demo.src.restaurant.model.PostRestaurantRes;
import com.example.demo.src.review.ReviewDao;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.example.demo.config.BaseResponseStatus.*;



@Service
public class RestaurantService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestaurantDao restaurantDao;

    private final ReviewDao reviewDao;

    private final RestaurantProvider restaurantProvider;

    private final JwtService jwtService;

    @Autowired
    public RestaurantService(RestaurantDao restaurantDao,RestaurantProvider restaurantProvider ,ReviewDao reviewDao,JwtService jwtService) {
        this.restaurantDao = restaurantDao;
        this.reviewDao = reviewDao;
        this.restaurantProvider = restaurantProvider;
        this.jwtService = jwtService;
    }

    public PostRestaurantRes createRestaurant(PostRestaurantReq postRestaurantReq) throws BaseException {

        try{

            int restaurantIdx = restaurantDao.createRestaurant(postRestaurantReq);

                restaurantDao.createRestaurantImage(restaurantIdx, postRestaurantReq);

            return new PostRestaurantRes(restaurantIdx);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyRestaurant(PostRestaurantReq restaurant, int restaurantId) throws BaseException {
        try{


            int result = restaurantDao.modifyRestaurant(restaurant ,restaurantId);

            restaurantDao.modifyRestaurantImage(restaurant,restaurantId);

            if(result == 0){
                throw new BaseException(MODIFY_FAIL_RESTAURANT);
            }

        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }
    public int deleteRestaurant(int restaurantId) throws BaseException{
        try{

            restaurantProvider.getRestaurantByRestaurantId(restaurantId);

            int result = restaurantDao.deleteRestaurant(restaurantId);

//            restaurantDao.deleteRestaurantImage(restaurantId);

            return result;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
