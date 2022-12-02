package com.example.demo.src.menu_option;

import com.example.demo.config.BaseException;
import com.example.demo.src.menu_option.model.PostMenuOptionRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class MenuOptionProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());


    private final MenuOptionDao menuOptionDao;

    private final JwtService jwtService;

    @Autowired
    public MenuOptionProvider(MenuOptionDao menuOptionDao, JwtService jwtService){
        this.menuOptionDao = menuOptionDao;
        this.jwtService = jwtService;
    }



    public List<PostMenuOptionRes> getAllMenuOptions() throws BaseException {

        try{
            List<PostMenuOptionRes> postMenuOptionRes = menuOptionDao.getAllMenuOptions();

            return postMenuOptionRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostMenuOptionRes getMenuOptionByMenuOptionId(int menuOptionId) throws BaseException {

        try{
            PostMenuOptionRes postMenuOptionRes = menuOptionDao.getMenuOptionByMenuOptionId(menuOptionId);
            return postMenuOptionRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<PostMenuOptionRes> getMenuOptionsByMenuId(int menuId) throws  BaseException{

        try{
            List<PostMenuOptionRes> postMenuOptionRes = menuOptionDao.getMenuOptionsByMenuId(menuId);

            return postMenuOptionRes;

        }catch (Exception exception){
            throw  new BaseException(DATABASE_ERROR);
        }
    }
}
