package com.example.demo.src.menu;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.menu.model.PostMenuReq;
import com.example.demo.src.menu.model.PostMenuRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/menus")
public class MenuController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MenuService menuService;
    @Autowired
    private final MenuProvider menuProvider;
    @Autowired
    private final JwtService jwtService;

    public MenuController(MenuService menuService,MenuProvider menuProvider,JwtService jwtService){
        this.menuService = menuService;
        this.menuProvider = menuProvider;
        this.jwtService = jwtService;
    }

    //jwt 적용??
    //널 값 확인 : 전부 다
    //가게 고유 번호 존재하는지 확인, D 혹은 M 입력, 내용 길이, 참조 값 존재 확인
    //가격 validation
    @ResponseBody
    @PostMapping("/{restaurantIdx}/join")
    public BaseResponse<PostMenuRes> creatMenu(@PathVariable("restaurantIdx") int restaurantIdx, @RequestBody PostMenuReq postMenuReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!

        try{

            if(postMenuReq.getGbCode() == null || postMenuReq.getGbCode().length() == 0) {
                return new BaseResponse<>(POST_MENU_EMPTY_NAME);
            }

            if(postMenuReq.getName() == null || postMenuReq.getName().length() == 0) {
                return new BaseResponse<>(POST_MENU_EMPTY_NAME);
            }

            PostMenuRes postMenuRes = menuService.createMenu(postMenuReq);

            return new BaseResponse<>(postMenuRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/modify/{restaurantIdx}/{menuIdx}")
    public BaseResponse<PostMenuRes> modifyMenu(@PathVariable("restaurantIdx") int restaurantIdx ,@PathVariable("menuIdx") int menuIdx, @RequestBody PostMenuReq postMenuReq){

        try{

            PostMenuRes postMenuRes = menuService.modifyMenu(postMenuReq);

            return new BaseResponse<>(postMenuRes);

        }catch(BaseException exception) {

            return new BaseResponse<>((exception.getStatus()));

        }
    }

    @ResponseBody
    @PatchMapping("/delete/{restaurantIdx}/{menuIdx}")
    public BaseResponse<Integer> deleteMenu(@PathVariable("restaurantIdx") int restaurantIdx, @PathVariable("menuIdx") int menuIdx){

        try {

            menuService.deleteMenu(menuIdx);

            return new BaseResponse<>(menuIdx);
        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/list") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<PostMenuRes>> getAllMenus(){

        try{
            List<PostMenuRes> getMenuRes = menuProvider.getAllMenus();
            return new BaseResponse<>(getMenuRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @GetMapping("/menu-id/{menuIdx}") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<PostMenuRes> getMenu(@PathVariable("menuIdx") int menuIdx){

        try{
            PostMenuRes postMenuRes = menuProvider.getMenuByMenuId(menuIdx);

            return new BaseResponse<>(postMenuRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @GetMapping("/res-id/{restaurantIdx}")
    public List<PostMenuRes> getRestaurantMenu(@PathVariable ("restaurantIdx") int restaurantIdx){

        try{
            List<PostMenuRes> postMenuRes = menuProvider.getRestaurantMenu(restaurantIdx);

            return postMenuRes;
        } catch(BaseException exception){
            return null;
        }
    }

}
