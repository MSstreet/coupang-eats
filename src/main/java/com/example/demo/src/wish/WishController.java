package com.example.demo.src.wish;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.wish.model.GetWishRes;
import com.example.demo.src.wish.model.PatchWishReq;
import com.example.demo.src.wish.model.PostWishReq;
import com.example.demo.src.wish.model.PostWishRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/wishlists")

public class WishController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final WishProvider wishProvider;
    @Autowired
    private final WishService wishService;
    @Autowired
    private final JwtService jwtService;

    public WishController(WishProvider wishProvider, WishService wishService, JwtService jwtService) {
        this.wishProvider = wishProvider;
        this.wishService = wishService;
        this.jwtService = jwtService;
    }

    /**
     * 찜 등록 API
     * [POST] /wishlists
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostWishRes> createWish(@RequestBody PostWishReq postWishReq){
        if (postWishReq.getUserIdx() == null){
            return new BaseResponse<>(POST_WISHLISTS_EMPTY_USER);
        }
        else if (postWishReq.getUserIdx() < 1) {
            return new BaseResponse<>(POST_WISHLISTS_INVALID_USER);
        }
        else if (postWishReq.getRestIdx() == null) {
            return new BaseResponse<>(POST_WISHLISTS_EMPTY_RESTAURANT);
        }
        else if (postWishReq.getRestIdx() < 1) {
            return new BaseResponse<>(POST_WISHLISTS_INVALID_RESTAURANT);
        }
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(postWishReq.getUserIdx() != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostWishRes postWishRes = wishService.createWish(postWishReq);
            return new BaseResponse<>(postWishRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 모든 찜들 조회 API
     * [GET] /wishlists
     * paging : ?pageNum={pageNum}&count={count}
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetWishRes>> getWishes(@RequestParam(required = false, defaultValue = "0") String pageNum, @RequestParam(required = false, defaultValue = "100") String count) {
        try {
            List<GetWishRes> getWishRes = wishProvider.getWishes(Integer.parseInt(pageNum)*Integer.parseInt(count), Integer.parseInt(count));
            return new BaseResponse<>(getWishRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 찜 하나 조회 API
     * [GET] /wishlists/:wishIdx
     */
    @ResponseBody
    @GetMapping("/{wishIdx}")
    public BaseResponse<GetWishRes> getWish(@PathVariable("wishIdx") int wishIdx) {
        try {
            GetWishRes getWishRes = wishProvider.getWish(wishIdx);
            return new BaseResponse<>(getWishRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 사용자의 찜 조회 API
     * [GET] /wishlists/users/:userIdx
     * paging : ?pageNum={pageNum}&count={count}
     */
    @ResponseBody
    @GetMapping("/users/{userIdx}")
    public BaseResponse<List<GetWishRes>> getWishesByUser(@PathVariable("userIdx") int userIdx, @RequestParam(required = false, defaultValue = "0") String pageNum, @RequestParam(required = false, defaultValue = "100") String count) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetWishRes> getWishRes = wishProvider.getWishesByUser(userIdx, Integer.parseInt(pageNum)*Integer.parseInt(count), Integer.parseInt(count));
            return new BaseResponse<>(getWishRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 사용자의 찜 수 조회 API
     * [GET] /wishlists/users/:userIdx/count
     */
    @ResponseBody
    @GetMapping("/users/{userIdx}/count")
    public BaseResponse<Integer> getWishesCountByUser(@PathVariable("userIdx") int userIdx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            int count = wishProvider.getWishesCountByUser(userIdx);
            return new BaseResponse<>(count);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 가게의 찜 수 조회 API
     * [GET] /wishlists/restaurants/:restIdx/count
     */
    @ResponseBody
    @GetMapping("/restaurants/{restIdx}/count")
    public BaseResponse<Integer> getWishesCountByRest(@PathVariable("restIdx") int restIdx) {
        try {
            int count = wishProvider.getWishesCountByRest(restIdx);
            return new BaseResponse<>(count);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 찜삭제 API
     * [PATCH] /wishlists/:wishIdx/d
     */
    @ResponseBody
    @PatchMapping("/{wishIdx}/d")
    public BaseResponse<Integer> deleteWish(@PathVariable("wishIdx") int wishIdx) {
        try {
            int wishIdxByJwt = jwtService.getUserIdx();
            int userIdx = wishProvider.getUserIdxByWish(wishIdx);
            if(userIdx != wishIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            wishService.deleteWish(wishIdx);
            return new BaseResponse<>(wishIdx);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
