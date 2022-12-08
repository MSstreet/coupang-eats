package com.example.demo.src.menu;

import com.example.demo.config.BaseException;
import com.example.demo.src.menu.model.PostMenuRes;
import com.example.demo.src.restaurant.model.GetRestaurantRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class MenuProvider {

    private final MenuDao menuDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MenuProvider(MenuDao menuDao, JwtService jwtService) {
        this.menuDao = menuDao;
        this.jwtService = jwtService;
    }


    public List<PostMenuRes> getAllMenus(int offset, int limit) throws BaseException {

        try{
            List<PostMenuRes> getMenuRes = menuDao.getAllMenus(offset,limit);
            return getMenuRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostMenuRes getMenuByMenuId(int menuId) throws BaseException {

        try{
            PostMenuRes postMenuRes = menuDao.getMenuByMenuId(menuId);

            return postMenuRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<PostMenuRes> getRestaurantMenu(int restaurantId,int offset, int limit) throws  BaseException{

        try{
            List<PostMenuRes> postMenuRes = menuDao.getRestaurantMenu(restaurantId,offset,limit);

            return postMenuRes;

        }catch (Exception exception){
            throw  new BaseException(DATABASE_ERROR);
        }
    }


    public List<PostMenuRes> getMenusByMenuName(String menu,int offset, int limit) throws BaseException {

        try{
            List<PostMenuRes> postMenuRes = menuDao.getMenusByName(menu,offset,limit);

            return postMenuRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
