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

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

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
    @PostMapping("/{menuIdx}/join")
    public BaseResponse<PostMenuOptionRes> createMenuOption(@PathVariable("menuIdx") int menuIdx, @RequestBody PostMenuOptionReq postMenuOptionReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        try{

            PostMenuOptionRes postMenuOptionRes = menuOptionService.createMenuOption(postMenuOptionReq);

            return new BaseResponse<>(postMenuOptionRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PutMapping("/modify/{menuOptionIdx}")
    public BaseResponse<PostMenuOptionRes> modifyMenuOption(@PathVariable("menuOptionIdx") int menuOptionIdx, @RequestBody PostMenuOptionReq postMenuOptionReq){
        try{

            PostMenuOptionRes postMenuOptionRes = menuOptionService.modifyMenuOption(postMenuOptionReq);

            return new BaseResponse<>(postMenuOptionRes);

        }catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/delete/{menuOptionIdx}")
    public BaseResponse<Integer> deleteDeleteMenuOption(@PathVariable("menuOptionIdx") int menuOptionIdx){
        try {

            menuOptionService.deleteMenuOption(menuOptionIdx);

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
            return new BaseResponse<>(getMenuOptionRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @GetMapping("/menu-option/{menuOptionIdx}") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<PostMenuOptionRes> getMenuOptionByMenuOptionId(@PathVariable("menuOptionIdx") int menuOptionIdx){

        try{
            PostMenuOptionRes postMenuOptionRes = menuOptionProvider.getMenuOptionByMenuOptionId(menuOptionIdx);

            return new BaseResponse<>(postMenuOptionRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @GetMapping("/menu-id/{menuIdx}")
    public List<PostMenuOptionRes> getMenuOptionByMenuId(@PathVariable ("menuIdx") int menuIdx){

        try{
            List<PostMenuOptionRes> postMenuOptionRes = menuOptionProvider.getMenuOptionsByMenuId(menuIdx);

            return postMenuOptionRes;
        } catch(BaseException exception){
            return null;
        }
    }
}
