package com.example.demo.src.restaurant;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.menu.MenuDao;
import com.example.demo.src.menu.model.PostMenuRes;
import com.example.demo.src.restaurant.model.GetRestaurantRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class RestaurantProvider {
    private final RestaurantDao restaurantDao;

    private final MenuDao menuDao;
    private final JwtService jwtService;

    @Autowired
    public RestaurantProvider(RestaurantDao restaurantDao,MenuDao menuDao, JwtService jwtService) {
        this.restaurantDao = restaurantDao;
        this.menuDao = menuDao;
        this.jwtService = jwtService;
    }
    public int checkBusinessNum(String BusinessNum) throws BaseException {

        try{

            int check = restaurantDao.checkBusinessNum(BusinessNum);

            if(check == 1){
                System.out.println("확인");
                throw new BaseException(POST_RESTAURANT_EXISTS_BUSINESS_NUMBER);
            }

            return check;

        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetRestaurantRes> getAllRestaurants() throws BaseException {

        try{
            List<GetRestaurantRes> getRestaurantRes = restaurantDao.getAllRestaurants();

            return getRestaurantRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetRestaurantRes getRestaurantByRestaurantId(int restaurantId) throws BaseException{
        try {
            GetRestaurantRes getRestaurantRes = restaurantDao.getRestaurantByRestaurantId(restaurantId);

            return getRestaurantRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public List<GetRestaurantRes> getRestaurantsByNameSearch(String searchRestaurantNameReq) throws BaseException {

        try{
            List<GetRestaurantRes> getRestaurantRes = restaurantDao.getRestaurantsByNameSearch(searchRestaurantNameReq);

            return getRestaurantRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetRestaurantRes> getRestaurantsByCategorySearch(int category) throws BaseException {

        try{
            List<GetRestaurantRes> getRestaurantRes = restaurantDao.getRestaurantsByCategorySearch(category);

            return getRestaurantRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetRestaurantRes> getRestaurantsByMenuSearch(String menu) throws BaseException {

        try{
            List<PostMenuRes> postMenuRes = menuDao.getMenusByName(menu);

            List<GetRestaurantRes> getRestaurantRes = new ArrayList<>();

            for(int i = 0; i < postMenuRes.size(); i++){
                GetRestaurantRes getRestaurantRes1 = restaurantDao.getRestaurantByRestaurantId(postMenuRes.get(i).getRestaurantId());
                getRestaurantRes.add(getRestaurantRes1);
            }

            for(int i = 0; i < postMenuRes.size(); i++){
                System.out.println(getRestaurantRes.get(i));
            }

            return getRestaurantRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetRestaurantRes> getRestaurantsByAddress(String address) throws BaseException {

        try{

            List<GetRestaurantRes> getRestaurantRes = restaurantDao.getRestaurantsByAddress(address);

            return getRestaurantRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
