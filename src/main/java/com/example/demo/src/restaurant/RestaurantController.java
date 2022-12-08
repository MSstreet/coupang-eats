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

import javax.validation.Valid;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexRestaurantNumber;

@RestController
@RequestMapping("/app/restaurants")
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

//    @ResponseBody
//    @PostMapping("/{userId}/join")
//    public BaseResponse<PostRestaurantRes> createRestaurant(@PathVariable("userId") int userId, @RequestBody PostRestaurantReq postRestaurantReq) {
//        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
//
//        try{
//            int userIdxByJwt = jwtService.getUserIdx();
//
//            if(userId != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
//        }catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//
//        //널 값 : 주소, 전화번호, 대표자명, 사업자번호, 운영시간, 배달팁, 최소주문가격, 레스토랑 사진, 치타, 배달, 포장
//        //empty 보류 주소, 사진, 치타, 배달, 포장
//        //형식 : 전화번호
//        //중복 : 사업자 번호
//        try{
//            if(postRestaurantReq.getName() == null || postRestaurantReq.getName().length() == 0) {
//                return new BaseResponse<>(POST_RESTAURANT_EMPTY_RESTAURANT_NAME);
//            }
//
//            //닉네임 널 여부
//            if(postRestaurantReq.getNumber() == null || postRestaurantReq.getNumber().length() == 0) {
//                return new BaseResponse<>(POST_RESTAURANT_EMPTY_NUMBER);
//            }
//
//            if(postRestaurantReq.getRepresentName() == null || postRestaurantReq.getRepresentName().length() == 0) {
//                return new BaseResponse<>(POST_RESTAURANT_EMPTY_REPRESENT);
//            }
//
//            if(postRestaurantReq.getCompanyRegistrationNumber() == null || postRestaurantReq.getCompanyRegistrationNumber().length() == 0) {
//                return new BaseResponse<>(POST_RESTAURANT_EMPTY_BUSINESS_NUMBER);
//            }
//
//            if(postRestaurantReq.getOperationTime() == null || postRestaurantReq.getOperationTime().length() == 0) {
//                return new BaseResponse<>(POST_RESTAURANT_EMPTY_OPERATION);
//            }
//
//            if(postRestaurantReq.getTipDelivery() == null || postRestaurantReq.getTipDelivery().length() == 0) {
//                return new BaseResponse<>(POST_RESTAURANT_EMPTY_DELIVERY_TIP);
//            }
//            if(postRestaurantReq.getMinDeliveryPrice() == null || postRestaurantReq.getMinDeliveryPrice().length() == 0) {
//                return new BaseResponse<>(POST_RESTAURANT_EMPTY_MINIMUM_ORDER_PRICE);
//            }
//
//            else if (!isRegexPhone(postRestaurantReq.getNumber())) {
//                return new BaseResponse<>(POST_RESTAURANT_INVALID_NUMBER);
//            }
//             PostRestaurantRes postRestaurantRes = restaurantService.createRestaurant(postRestaurantReq);
//
//            return new BaseResponse<>(postRestaurantRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }

    @ResponseBody
    @PostMapping("/{userIdx}")
    public BaseResponse<PostRestaurantRes> createRestaurant(@PathVariable("userIdx") int userIdx, @RequestBody PostRestaurantReq postRestaurantReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!

        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

        //널 값 : 주소, 전화번호, 대표자명, 사업자번호, 운영시간, 배달팁, 최소주문가격, 레스토랑 사진, 치타, 배달, 포장

        try{
            if(postRestaurantReq.getName() == null || postRestaurantReq.getName().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_RESTAURANT_NAME);
            }


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
            ////////////////////////////////////
//            if(postRestaurantReq.getTipDelivery() == null || postRestaurantReq.getTipDelivery().length() == 0) {
//                return new BaseResponse<>(POST_RESTAURANT_EMPTY_DELIVERY_TIP);
//            }
            if(postRestaurantReq.getMinDeliveryPrice() == null || postRestaurantReq.getMinDeliveryPrice().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_MINIMUM_ORDER_PRICE);
            }

            if(postRestaurantReq.getAddress() == null || postRestaurantReq.getAddress().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_ADDRESS);
            }

            if (!isRegexRestaurantNumber(postRestaurantReq.getNumber())) {
                return new BaseResponse<>(POST_RESTAURANT_INVALID_NUMBER);
            }
            PostRestaurantRes postRestaurantRes = restaurantService.createRestaurant(postRestaurantReq);

            return new BaseResponse<>(postRestaurantRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

//    @ResponseBody
//    @PatchMapping("/modify/{userId}/{restiIdx}")
//    public BaseResponse<String> modifyRestaurant(@PathVariable("userId") int userId, @PathVariable("restiIdx") int restiIdx, @RequestBody PostRestaurantReq postRestaurantReq){
//
//        try{
//            int userIdxByJwt = jwtService.getUserIdx();
//
//            if(userId != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
//        }catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//
//        try{
//            if(postRestaurantReq.getName() == null || postRestaurantReq.getName().length() == 0) {
//                return new BaseResponse<>(POST_RESTAURANT_EMPTY_RESTAURANT_NAME);
//            }
//
//            //닉네임 널 여부
//            if(postRestaurantReq.getNumber() == null || postRestaurantReq.getNumber().length() == 0) {
//                return new BaseResponse<>(POST_RESTAURANT_EMPTY_NUMBER);
//            }
//
//            if(postRestaurantReq.getRepresentName() == null || postRestaurantReq.getRepresentName().length() == 0) {
//                return new BaseResponse<>(POST_RESTAURANT_EMPTY_REPRESENT);
//            }
//
//            if(postRestaurantReq.getCompanyRegistrationNumber() == null || postRestaurantReq.getCompanyRegistrationNumber().length() == 0) {
//                return new BaseResponse<>(POST_RESTAURANT_EMPTY_BUSINESS_NUMBER);
//            }
//
//            if(postRestaurantReq.getOperationTime() == null || postRestaurantReq.getOperationTime().length() == 0) {
//                return new BaseResponse<>(POST_RESTAURANT_EMPTY_OPERATION);
//            }
//
//            if(postRestaurantReq.getTipDelivery() == null || postRestaurantReq.getTipDelivery().length() == 0) {
//                return new BaseResponse<>(POST_RESTAURANT_EMPTY_DELIVERY_TIP);
//            }
//            if(postRestaurantReq.getMinDeliveryPrice() == null || postRestaurantReq.getMinDeliveryPrice().length() == 0) {
//                return new BaseResponse<>(POST_RESTAURANT_EMPTY_MINIMUM_ORDER_PRICE);
//            }
//
//            else if (!isRegexPhone(postRestaurantReq.getNumber())) {
//                return new BaseResponse<>(POST_RESTAURANT_INVALID_NUMBER);
//            }
//            restaurantService.modifyRestaurant(postRestaurantReq, restiIdx);
//
//            String result = "가게 정보를 수정하였습니다.";
//
//            return new BaseResponse<>(result);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }


    //널 값 : 주소, 전화번호, 대표자명, 사업자번호, 운영시간, 배달팁, 최소주문가격, 레스토랑 사진, 치타, 배달, 포장
    @ResponseBody
    @PatchMapping("/{userIdx}/{restIdx}")
    public BaseResponse<String> modifyRestaurant(@PathVariable("userIdx") int userIdx, @PathVariable("restIdx") int restIdx, @RequestBody PostRestaurantReq postRestaurantReq){

        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

        try{
            if(postRestaurantReq.getName() == null || postRestaurantReq.getName().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_RESTAURANT_NAME);
            }

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
            //////////////////////////
            if(postRestaurantReq.getTipDelivery() == null || postRestaurantReq.getTipDelivery().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_DELIVERY_TIP);
            }
            if(postRestaurantReq.getMinDeliveryPrice() == null || postRestaurantReq.getMinDeliveryPrice().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_MINIMUM_ORDER_PRICE);
            }

            if(postRestaurantReq.getAddress() == null || postRestaurantReq.getAddress().length() == 0) {
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_ADDRESS);
            }

            if (!isRegexRestaurantNumber(postRestaurantReq.getNumber())) {
                return new BaseResponse<>(POST_RESTAURANT_INVALID_NUMBER);
            }
            System.out.println("확인!!!");
            restaurantService.modifyRestaurant(postRestaurantReq, restIdx);

            String result = "가게 정보를 수정하였습니다.";

            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

//    @ResponseBody
//    @PatchMapping("/delete/{userId}/{restiIdx}")
//    public BaseResponse<String> deleteRestaurant(@PathVariable("userId") int userId,@PathVariable("restiIdx") int restiIdx){
//
//        try{
//            int userIdxByJwt = jwtService.getUserIdx();
//
//            if(userId != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
//        }catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//
//        try{
//            restaurantService.deleteRestaurant(restiIdx);
//
//            String result = "레스토랑을 삭제하였습니다.";
//
//            return new BaseResponse<>(result);
//
//
//        }catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }


    //벨러데이션
    @ResponseBody
    @PatchMapping("/{userIdx}/{restIdx}/d")
    public BaseResponse<Integer> deleteRestaurant(@PathVariable("userIdx") int userIdx, @PathVariable("restIdx") int restIdx){

        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }


        try{

            int result = restaurantService.deleteRestaurant(restIdx);

            if(result == 0){
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_RESTAURANT);
            }

            return new BaseResponse<>(restIdx);


        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/list") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetRestaurantRes>> getAllRestaurants(@RequestParam(required = false, defaultValue = "0") String pageNum, @RequestParam(required = false, defaultValue = "100") String count) {
        try{
            List<GetRestaurantRes> getRestaurantRes = restaurantProvider.getAllRestaurants(Integer.parseInt(pageNum)*Integer.parseInt(count), Integer.parseInt(count));

            if(getRestaurantRes.size() == 0){
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_RESTAURANT);
            }

            return new BaseResponse<>(getRestaurantRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //존재하지 않는 레스토랑(필요x)
    @ResponseBody
    @GetMapping("/{restIdx}") // (GET) 127.0.0.1:9000/app/users/:userIdx
    public BaseResponse<GetRestaurantRes> getRestaurantByRestaurantId(@PathVariable("restIdx") int restIdx) {

        try{
            GetRestaurantRes getRestaurantRes = restaurantProvider.getRestaurantByRestaurantId(restIdx);

            if(getRestaurantRes == null){
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_RESTAURANT);
            }

            return new BaseResponse<>(getRestaurantRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    //검색결과가 존재하지 않을 때(필요x)
    @ResponseBody
    @GetMapping("/name") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetRestaurantRes>> getRestaurantsByNameSearch(@RequestParam String name,@RequestParam(required = false, defaultValue = "0") String pageNum, @RequestParam(required = false, defaultValue = "100") String count) {
        try{

            List<GetRestaurantRes> getRestaurantRes = restaurantProvider.getRestaurantsByNameSearch(name,Integer.parseInt(pageNum)*Integer.parseInt(count), Integer.parseInt(count));

            if(getRestaurantRes.size() == 0){
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_RESTAURANT);
            }

            return new BaseResponse<>(getRestaurantRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //존재하지 않는 카테고리(필요x)
    @ResponseBody
    @GetMapping("/category") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetRestaurantRes>> getRestaurantsByCategorySearch(@RequestParam String category,@RequestParam(required = false, defaultValue = "0") String pageNum, @RequestParam(required = false, defaultValue = "100") String count) {
        try{

            List<GetRestaurantRes> getRestaurantRes = restaurantProvider.getRestaurantsByCategorySearch(category,Integer.parseInt(pageNum)*Integer.parseInt(count), Integer.parseInt(count));

            if(getRestaurantRes.size() == 0){
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_RESTAURANT);
            }

            return new BaseResponse<>(getRestaurantRes);



        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/menu") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetRestaurantRes>> getRestaurantsByMenuSearch(@RequestParam String menu,@RequestParam(required = false, defaultValue = "0") String pageNum, @RequestParam(required = false, defaultValue = "100") String count) {
        try{

            List<GetRestaurantRes> getRestaurantRes = restaurantProvider.getRestaurantsByMenuSearch(menu,Integer.parseInt(pageNum)*Integer.parseInt(count), Integer.parseInt(count));

            if(getRestaurantRes.size() == 0){
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_RESTAURANT);
            }

            return new BaseResponse<>(getRestaurantRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetRestaurantRes>> getRestaurantsByAddress(@RequestParam String address, @RequestParam(required = false, defaultValue = "0") String pageNum, @RequestParam(required = false, defaultValue = "100") String count) {
        try{

            List<GetRestaurantRes> getRestaurantRes = restaurantProvider.getRestaurantsByAddress(address,Integer.parseInt(pageNum)*Integer.parseInt(count), Integer.parseInt(count));

            if(getRestaurantRes.size() == 0){
                return new BaseResponse<>(POST_RESTAURANT_EMPTY_RESTAURANT);
            }

            return new BaseResponse<>(getRestaurantRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }



}
