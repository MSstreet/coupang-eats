package com.example.demo.src.menu;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.menu.model.PostMenuReq;
import com.example.demo.src.menu.model.PostMenuRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class MenuService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MenuDao menuDao;

    private final JwtService jwtService;

    @Autowired
    public MenuService(MenuDao menuDao, JwtService jwtService){
        this.menuDao = menuDao;
        this.jwtService = jwtService;
    }

    public PostMenuRes createMenu(PostMenuReq postMenuReq) throws BaseException {

        try {

            int menuId = menuDao.createMenu(postMenuReq);

            PostMenuRes postMenuRes = menuDao.getMenuByMenuId(menuId);
            return postMenuRes;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostMenuRes modifyMenu(PostMenuReq postMenuReq) throws BaseException {

        try{
            int result = menuDao.modifyMenu(postMenuReq);

            if(result == 0){
                throw new BaseException(MODIFY_FAIL_RESTAURANT);
            }

            PostMenuRes postMenuRes = menuDao.getMenuByMenuId(postMenuReq.getMenuId());

            return postMenuRes;

        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteMenu(int menuId) throws BaseException{
        try{

            int result = menuDao.deleteMenu(menuId);

            if(result == 0){
                throw new BaseException(FAILED_TO_MODIFY);
            }

        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
