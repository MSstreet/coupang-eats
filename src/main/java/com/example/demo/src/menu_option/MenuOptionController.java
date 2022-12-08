package com.example.demo.src.menu_option;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.menu_option.model.PostMenuOptionReq;
import com.example.demo.src.menu_option.model.PostMenuOptionRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/menu-options")
public class MenuOptionController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MenuOptionService menuOptionService;
    @Autowired
    private final MenuOptionProvider menuOptionProvider;
    @Autowired
    private final MenuOptionDao menuOptionDao;
    @Autowired
    private final JwtService jwtService;

    public MenuOptionController(MenuOptionService menuOptionService, MenuOptionDao menuOptionDao, MenuOptionProvider menuOptionProvider,JwtService jwtService){
        this.menuOptionService = menuOptionService;
        this.menuOptionDao = menuOptionDao;
        this.menuOptionProvider = menuOptionProvider;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @PostMapping("/{userIdx}/{menuIdx}/join")
    public BaseResponse<PostMenuOptionRes> createMenuOption(@PathVariable("userIdx") int userIdx, @RequestBody PostMenuOptionReq postMenuOptionReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

        try{

            if(postMenuOptionReq.getOptionName() == null ||postMenuOptionReq.getOptionName().length() == 0) {
                return new BaseResponse<>(POST_MENU_OPTION_EMPTY_NAME);
            }

            PostMenuOptionRes postMenuOptionRes = menuOptionService.createMenuOption(postMenuOptionReq);

            return new BaseResponse<>(postMenuOptionRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PutMapping("/modify/{userIdx}/{menuOptionIdx}")
    public BaseResponse<PostMenuOptionRes> modifyMenuOption(@PathVariable("userIdx") int userIdx, @PathVariable("menuOptionIdx") int menuOptionIdx, @RequestBody PostMenuOptionReq postMenuOptionReq){
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

        try{

            if(postMenuOptionReq.getOptionName() == null ||postMenuOptionReq.getOptionName().length() == 0) {
                return new BaseResponse<>(POST_MENU_OPTION_EMPTY_NAME);
            }

            PostMenuOptionRes postMenuOptionRes = menuOptionService.modifyMenuOption(postMenuOptionReq);

            return new BaseResponse<>(postMenuOptionRes);

        }catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/delete/{userIdx}/{menuOptionIdx}")
    public BaseResponse<Integer> deleteDeleteMenuOption(@PathVariable("userIdx") int userIdx, @PathVariable("menuOptionIdx") int menuOptionIdx){

        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

        try {

            int result = menuOptionService.deleteMenuOption(menuOptionIdx);

            if(result == 0){
                return new BaseResponse<>(POST_MENU_OPTION_EMPTY_MENU_OPTION);
            }
            return new BaseResponse<>(menuOptionIdx);

        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/list") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<PostMenuOptionRes>> getAllMenuOptions(){

        try{
            List<PostMenuOptionRes> getMenuOptionRes = menuOptionProvider.getAllMenuOptions();

            if(getMenuOptionRes.size() == 0){
                return new BaseResponse<>(POST_MENU_OPTION_EMPTY_MENU_OPTION);
            }

            return new BaseResponse<>(getMenuOptionRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @GetMapping("/{menuOptionIdx}") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<PostMenuOptionRes> getMenuOptionByMenuOptionId(@PathVariable("menuOptionIdx") int menuOptionIdx){

        try{
            PostMenuOptionRes postMenuOptionRes = menuOptionProvider.getMenuOptionByMenuOptionId(menuOptionIdx);

            if(postMenuOptionRes == null){
                return new BaseResponse<>(POST_MENU_OPTION_EMPTY_MENU_OPTION);
            }

            return new BaseResponse<>(postMenuOptionRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

////////////////////////////////////////
    @ResponseBody
    @GetMapping("/menu/{menuIdx}")
    public BaseResponse<List<PostMenuOptionRes>> getMenuOptionByMenuId(@PathVariable ("menuIdx") int menuIdx){

        try{
            List<PostMenuOptionRes> postMenuOptionRes = menuOptionProvider.getMenuOptionsByMenuId(menuIdx);

            if(postMenuOptionRes.size() == 0){
                return new BaseResponse<>(POST_MENU_OPTION_EMPTY_MENU_OPTION);
            }

            return new BaseResponse<>(postMenuOptionRes);
        } catch(BaseException exception){
            return null;
        }
    }
}
