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

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/menu")
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
    //가격 validation
    @ResponseBody
    @PostMapping("/{restaurantId}/join")
    public BaseResponse<PostMenuRes> creatMenuOptionDetail(@PathVariable("restaurantId") int restaurantId, @RequestBody PostMenuReq postMenuReq) {
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
    @PutMapping("/modify/{restaurantId}/{menuId}")
    public BaseResponse<PostMenuRes> modifyMenu(@PathVariable("restaurantId") int restaurantId ,@PathVariable("menuId") int menuId, @RequestBody PostMenuReq postMenuReq){

        try{

            PostMenuRes postMenuRes = menuService.modifyMenu(postMenuReq);



            return new BaseResponse<>(postMenuRes);

        }catch(BaseException exception) {

            return new BaseResponse<>((exception.getStatus()));

        }
    }

    @ResponseBody
    @PatchMapping("/delete/{restaurantId}/{menuId}")
    public BaseResponse<Integer> deleteMenu(@PathVariable("restaurantId") int restaurantId, @PathVariable("menuId") int menuId){

        try {

            menuService.deleteMenu(menuId);

            return new BaseResponse<>(menuId);
        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }



}
