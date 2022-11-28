package com.example.demo.src.restaurant;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.restaurant.model.PostRestaurantReq;
import com.example.demo.src.restaurant.model.PostRestaurantRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app/restaurant")
public class RestaurantController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final RestaurantService restaurantService;

    @Autowired
    private final RestaurantProvider restaurantProvider;

    @Autowired
    private final JwtService jwtService;

    public RestaurantController(RestaurantService restaurantService,RestaurantProvider restaurantProvider,JwtService jwtService){
        this.restaurantService = restaurantService;
        this.restaurantProvider = restaurantProvider;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @PostMapping("/join")
    public BaseResponse<PostRestaurantRes> createRestaurant(@RequestBody PostRestaurantReq postRestaurantReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!

        try{
//            int userIdxByJwt = jwtService.getUserIdx();
//
//            if(userId != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }

            //validation 추가하기

            PostRestaurantRes postRestaurantRes = restaurantService.createRestaurant(postRestaurantReq);

            return new BaseResponse<>(postRestaurantRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
