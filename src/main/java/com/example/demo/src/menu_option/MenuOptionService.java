package com.example.demo.src.menu_option;

import com.example.demo.config.BaseException;
import com.example.demo.src.menu_option.model.PostMenuOptionReq;
import com.example.demo.src.menu_option.model.PostMenuOptionRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class MenuOptionService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());


    private final MenuOptionDao menuOptionDao;

    private final JwtService jwtService;

    @Autowired
    public MenuOptionService(MenuOptionDao menuOptionDao, JwtService jwtService){
        this.menuOptionDao = menuOptionDao;
        this.jwtService = jwtService;
    }

    public PostMenuOptionRes createMenuOption(PostMenuOptionReq postMenuOptionReq) throws BaseException {

        try {

            int menuOptionId = menuOptionDao.createMenuOption(postMenuOptionReq);

            PostMenuOptionRes postMenuOptionRes = menuOptionDao.getMenuOptionByMenuOptionId(menuOptionId);

            return postMenuOptionRes;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostMenuOptionRes modifyMenuOption(PostMenuOptionReq postMenuOptionReq) throws BaseException {
        try{
            int result = menuOptionDao.modifyMenuOption(postMenuOptionReq);

            if(result == 0){
                throw new BaseException(MODIFY_FAIL_RESTAURANT);
            }
            PostMenuOptionRes postMenuOptionRes = menuOptionDao.getMenuOptionByMenuOptionId(postMenuOptionReq.getMenuOptionId());

            return postMenuOptionRes;

        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int deleteMenuOption(int menuOptionId) throws BaseException{
        try{

            int result = menuOptionDao.deleteMenuOption(menuOptionId);

            if(result == 0){
                throw new BaseException(FAILED_TO_MODIFY);
            }

            return result;

        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
