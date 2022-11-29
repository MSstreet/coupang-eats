package com.example.demo.src.restaurant;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.restaurant.model.GetRestaurantRes;
import com.example.demo.src.restaurant.model.PostRestaurantReq;
import com.example.demo.src.restaurant.model.PostRestaurantRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexPhone;
////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
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
//        }catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }

        //널 값 : 주소, 전화번호, 대표자명, 사업자번호, 운영시간, 배달팁, 최소주문가격, 레스토랑 사진, 치타, 배달, 포장
        //empty 보류 주소, 사진, 치타, 배달, 포장
        //형식 : 전화번호
        //중복 : 사업자 번호
        try{
            if(postRestaurantReq.getName() == null || postRestaurantReq.getName().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_RESTAURANT_NAME);
            }

            //닉네임 널 여부
            if(postRestaurantReq.getNumber() == null || postRestaurantReq.getNumber().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_NUMBER);
            }

            if(postRestaurantReq.getRepresentName() == null || postRestaurantReq.getRepresentName().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_REPRESENT);
            }

            if(postRestaurantReq.getCompanyRegistrationNumber() == null || postRestaurantReq.getCompanyRegistrationNumber().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_BUSINESS_NUMBER);
            }

            if(postRestaurantReq.getOperationTime() == null || postRestaurantReq.getOperationTime().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_OPERATION);
            }

            if(postRestaurantReq.getTipDelivery() == null || postRestaurantReq.getTipDelivery().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_DELIVERY_TIP);
            }
            if(postRestaurantReq.getMinDeliveryPrice() == null || postRestaurantReq.getMinDeliveryPrice().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_MINIMUM_ORDER_PRICE);
            }

            else if (!isRegexPhone(postRestaurantReq.getNumber())) {
                return new BaseResponse<>(POST_RESTAURANT_INVALID_NUMBER);
            }
             PostRestaurantRes postRestaurantRes = restaurantService.createRestaurant(postRestaurantReq);

            return new BaseResponse<>(postRestaurantRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/modify/{restaurantId}")
    public BaseResponse<String> modifyRestaurant(@PathVariable("restaurantId") int restaurantId, @RequestBody PostRestaurantReq postRestaurantReq){

//        try{
//            int userIdxByJwt = jwtService.getUserIdx();
//
//            if(userId != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
//        }catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }

        try{
            if(postRestaurantReq.getName() == null || postRestaurantReq.getName().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_RESTAURANT_NAME);
            }

            //닉네임 널 여부
            if(postRestaurantReq.getNumber() == null || postRestaurantReq.getNumber().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_NUMBER);
            }

            if(postRestaurantReq.getRepresentName() == null || postRestaurantReq.getRepresentName().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_REPRESENT);
            }

            if(postRestaurantReq.getCompanyRegistrationNumber() == null || postRestaurantReq.getCompanyRegistrationNumber().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_BUSINESS_NUMBER);
            }

            if(postRestaurantReq.getOperationTime() == null || postRestaurantReq.getOperationTime().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_OPERATION);
            }

            if(postRestaurantReq.getTipDelivery() == null || postRestaurantReq.getTipDelivery().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_DELIVERY_TIP);
            }
            if(postRestaurantReq.getMinDeliveryPrice() == null || postRestaurantReq.getMinDeliveryPrice().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_MINIMUM_ORDER_PRICE);
            }

            else if (!isRegexPhone(postRestaurantReq.getNumber())) {
                return new BaseResponse<>(POST_RESTAURANT_INVALID_NUMBER);
            }
            restaurantService.modifyRestaurant(postRestaurantReq, restaurantId);

            String result = "가게 정보를 수정하였습니다.";

            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/delete/{restaurantId}")
    public BaseResponse<String> deleteRestaurant(@PathVariable("restaurantId") int restaurantId){

//        try{
//            int userIdxByJwt = jwtService.getUserIdx();
//
//            if(userId != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
//        }catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }

        try{
            restaurantService.deleteRestaurant(restaurantId);

            String result = "레스토랑을 삭제하였습니다.";

            return new BaseResponse<>(result);


        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/list") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetRestaurantRes>> getAllRestaurants() {
        try{
            List<GetRestaurantRes> getRestaurantRes = restaurantProvider.getAllRestaurants();
            return new BaseResponse<>(getRestaurantRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{restaurantId}") // (GET) 127.0.0.1:9000/app/users/:userIdx
    public GetRestaurantRes getRestaurantByRestaurantId(@PathVariable("restaurantId") int restaurantId) {

        try{
            GetRestaurantRes getRestaurantRes = restaurantProvider.getRestaurantByRestaurantId(restaurantId);
            return getRestaurantRes;
        } catch(BaseException exception){
            return null;
        }

    }

    @ResponseBody
    @GetMapping("/name_search") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetRestaurantRes>> getRestaurantsByNameSearch(@RequestParam String searchRestaurantNameReq) {
        try{

            List<GetRestaurantRes> getRestaurantRes = restaurantProvider.getRestaurantsByNameSearch(searchRestaurantNameReq);
            return new BaseResponse<>(getRestaurantRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}