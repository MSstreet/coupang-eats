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

//        try{
//            int userIdxByJwt = jwtService.getUserIdx();
//
//            if(userId != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }

        //validation 추가하기//
//        }catch (){

//        }

        try{

             PostRestaurantRes postRestaurantRes = restaurantService.createRestaurant(postRestaurantReq);

            return new BaseResponse<>(postRestaurantRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/{restaurantId}")
    public BaseResponse<String> modifyRestaurant(@PathVariable("restaurantId") int restaurantId, @RequestBody PostRestaurantReq postRestaurantReq){

//        try {
//            int userIdxByJwt = jwtService.getUserIdx();
//
//            if(userId != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }

        try{
            restaurantService.modifyRestaurant(postRestaurantReq, restaurantId);

            String result = "";

            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/delete/{restaurantId}")
    public BaseResponse<String> deleteRestaurant(@PathVariable("restaurantId") int restaurantId){

//        try {
//            int userIdxByJwt = jwtService.getUserIdx();
//
//            if(userId != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }

        try{
            restaurantService.deleteRestaurant(restaurantId);

            String result = "레스토랑을 삭제하였습니다.";

            return new BaseResponse<>(result);


        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
