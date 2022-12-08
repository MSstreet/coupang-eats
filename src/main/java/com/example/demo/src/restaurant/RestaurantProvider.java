package com.example.demo.src.restaurant;

import com.example.demo.config.BaseException;

import com.example.demo.src.menu.MenuDao;
import com.example.demo.src.menu.model.PostMenuRes;
import com.example.demo.src.restaurant.model.GetRestaurantRes;
import com.example.demo.src.review.ReviewDao;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class RestaurantProvider {
    private final RestaurantDao restaurantDao;

    private final ReviewDao reviewDao;
    private final MenuDao menuDao;
    private final JwtService jwtService;

    @Autowired
    public RestaurantProvider(RestaurantDao restaurantDao,MenuDao menuDao,ReviewDao reviewDao, JwtService jwtService) {
        this.restaurantDao = restaurantDao;
        this.reviewDao = reviewDao;
        this.menuDao = menuDao;
        this.jwtService = jwtService;
    }


    public List<GetRestaurantRes> getAllRestaurants(int offset, int limit) throws BaseException {

        try{
            List<GetRestaurantRes> getRestaurantRes = restaurantDao.getAllRestaurants(offset,limit);

            for(int i = 0; i < getRestaurantRes.size(); i++) {
                double tmp = reviewDao.getScore(getRestaurantRes.get(i).getRestaurantId());
                getRestaurantRes.get(i).setScore(tmp);
            }

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

    public List<GetRestaurantRes> getRestaurantsByNameSearch(String searchRestaurantNameReq,int offset, int limit) throws BaseException {

        try{
            List<GetRestaurantRes> getRestaurantRes = restaurantDao.getRestaurantsByNameSearch(searchRestaurantNameReq,offset,limit);

            for(int i = 0; i < getRestaurantRes.size(); i++) {
                double tmp = reviewDao.getScore(getRestaurantRes.get(i).getRestaurantId());
                getRestaurantRes.get(i).setScore(tmp);
            }

            return getRestaurantRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetRestaurantRes> getRestaurantsByCategorySearch(String category,int offset, int limit) throws BaseException {

        try{
            List<GetRestaurantRes> getRestaurantRes = restaurantDao.getRestaurantsByCategorySearch(category,offset,limit);

            System.out.println("확인하겠다");
            for(int i = 0; i < getRestaurantRes.size(); i++) {
                double tmp = reviewDao.getScore(getRestaurantRes.get(i).getRestaurantId());
                getRestaurantRes.get(i).setScore(tmp);
            }

            return getRestaurantRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetRestaurantRes> getRestaurantsByMenuSearch(String menu,int offset, int limit) throws BaseException {

        try{
            List<PostMenuRes> postMenuRes = menuDao.getMenusByName(menu,offset,limit);
            System.out.println("확인!!!");
            List<GetRestaurantRes> getRestaurantRes = new ArrayList<>();

            for(int i = 0; i < postMenuRes.size(); i++){
                GetRestaurantRes getRestaurantRes1 = restaurantDao.getRestaurantByRestaurantId(postMenuRes.get(i).getRestaurantId());
                getRestaurantRes.add(getRestaurantRes1);
            }
            System.out.println("확인!!!");



//            for(int i = 0; i < getRestaurantRes.size(); i++) {
//                double tmp = reviewDao.getScore(getRestaurantRes.get(i).getRestaurantId());
//                getRestaurantRes.get(i).setScore(tmp);
//            }




            return getRestaurantRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetRestaurantRes> getRestaurantsByAddress(String address,int offset, int limit) throws BaseException {

        try{

            List<GetRestaurantRes> getRestaurantRes = restaurantDao.getRestaurantsByAddress(address,offset,limit);

            for(int i = 0; i < getRestaurantRes.size(); i++) {
                double tmp = reviewDao.getScore(getRestaurantRes.get(i).getRestaurantId());
                getRestaurantRes.get(i).setScore(tmp);
            }

            return getRestaurantRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
