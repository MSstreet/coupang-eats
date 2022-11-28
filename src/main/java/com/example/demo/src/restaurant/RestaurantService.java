package com.example.demo.src.restaurant;

import com.example.demo.config.BaseException;
import com.example.demo.src.restaurant.model.PostRestaurantReq;
import com.example.demo.src.restaurant.model.PostRestaurantRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class RestaurantService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestaurantDao restaurantDao;

    private final JwtService jwtService;

    @Autowired
    public RestaurantService(RestaurantDao restaurantDao, JwtService jwtService) {
        this.restaurantDao = restaurantDao;
        this.jwtService = jwtService;
    }

    public PostRestaurantRes createRestaurant(PostRestaurantReq postRestaurantReq) throws BaseException {

        try{
            int restaurantIdx = restaurantDao.createRestaurant(postRestaurantReq);

            //jwt 발급.
            String jwt = jwtService.createJwt(restaurantIdx);

            return new PostRestaurantRes(jwt,restaurantIdx);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
