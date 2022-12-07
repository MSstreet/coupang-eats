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

            if(postMenuReq.getGbCode().equals("MN")) {
                int menuId = menuDao.createMenu(postMenuReq);

                menuDao.createMenuImage(menuId, postMenuReq);
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                PostMenuRes postMenuRes = menuDao.getMenuByMenuId(menuId);

                return postMenuRes;
            }

            else{
                int menuId = menuDao.createMenuDetailOption(postMenuReq);
                PostMenuRes postMenuRes = menuDao.getMenuByMenuId(menuId);
                return postMenuRes;
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostMenuRes modifyMenu(PostMenuReq postMenuReq,int menuIdx) throws BaseException {

        try{
            int result = menuDao.modifyMenu(postMenuReq);
            System.out.println("확인~!!!!!!!!!!!!!!!!!!!!!!!!");

            if(result == 0){
                throw new BaseException(MODIFY_FAIL_RESTAURANT);
            }

            menuDao.modifyMenuImage(postMenuReq,menuIdx);

            PostMenuRes postMenuRes = menuDao.getMenuByMenuId(postMenuReq.getMenuId());

            return postMenuRes;

        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int deleteMenu(int menuId) throws BaseException{
        try{

            int result = menuDao.deleteMenu(menuId);

//            menuDao.deleteMenuImage(menuId);

            return result;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
