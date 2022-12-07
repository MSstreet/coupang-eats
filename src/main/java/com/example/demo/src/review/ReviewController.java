package com.example.demo.src.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.review.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/reviews")

public class ReviewController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final ReviewProvider reviewProvider;
    @Autowired
    private final ReviewService reviewService;
    @Autowired
    private final JwtService jwtService;

    public ReviewController(ReviewProvider reviewProvider, ReviewService reviewService, JwtService jwtService) {
        this.reviewProvider = reviewProvider;
        this.reviewService = reviewService;
        this.jwtService = jwtService;
    }

    /**
     * 리뷰 등록 API
     * [POST] /reviews
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostReviewRes> createReview(@RequestBody PostReviewReq postReviewReq){
        if (postReviewReq.getOrderIdx() == null){
            return new BaseResponse<>(POST_REVIEWS_EMPTY_ORDER);
        }
        else if (postReviewReq.getOrderIdx() < 1) {
            return new BaseResponse<>(POST_REVIEWS_INVALID_ORDER);
        }
        else if (postReviewReq.getScore() == null) {
            return new BaseResponse<>(POST_REVIEWS_EMPTY_SCORE);
        }
        else if (postReviewReq.getScore() < 0 || postReviewReq.getScore() > 5) {
            return new BaseResponse<>(POST_REVIEWS_INVALID_SCORE);
        }
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            int userIdx = reviewProvider.getUserIdxByOrder(postReviewReq.getOrderIdx());
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostReviewRes postReviewRes = reviewService.createReview(postReviewReq);
            return new BaseResponse<>(postReviewRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 모든 리뷰들 조회 API
     * [GET] /reviews
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetReviewRes>> getReviews() {
        try {
            List<GetReviewRes> getReviewsRes = reviewProvider.getReviews();
            return new BaseResponse<>(getReviewsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 리뷰 하나 조회 API
     * [GET] /reviews/:reviewIdx
     */
    @ResponseBody
    @GetMapping("/{reviewIdx}")
    public BaseResponse<GetReviewRes> getReview(@PathVariable("reviewIdx") int reviewIdx) {
        try {
            GetReviewRes getReviewsRes = reviewProvider.getReview(reviewIdx);
            return new BaseResponse<>(getReviewsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 사용자의 리뷰 조회 API
     * [GET] /reviews/users/:userIdx
     */
    @ResponseBody
    @GetMapping("/users/{userIdx}")
    public BaseResponse<List<GetReviewRes>> getReviewsByUser(@PathVariable("userIdx") int userIdx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetReviewRes> getReviewRes = reviewProvider.getReviewsByUser(userIdx);
            return new BaseResponse<>(getReviewRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 사용자의 리뷰 수 조회 API
     * [GET] /reviews/users/:userIdx/count
     */
    @ResponseBody
    @GetMapping("/users/{userIdx}/count")
    public BaseResponse<Integer> getReviewsCountByUser(@PathVariable("userIdx") int userIdx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            int count = reviewProvider.getReviewsCountByUser(userIdx);
            return new BaseResponse<>(count);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 가게의 리뷰 조회 API
     * [GET] /reviews/restaurants/:restIdx
     */
    @ResponseBody
    @GetMapping("/restaurants/{restIdx}")
    public BaseResponse<List<GetReviewRes>> getReviewsByRest(@PathVariable("restIdx") int restIdx) {
        try {
            List<GetReviewRes> getReviewRes = reviewProvider.getReviewsByRest(restIdx);
            return new BaseResponse<>(getReviewRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 가게의 리뷰 수 조회 API
     * [GET] /reviews/restaurants/:restIdx/count
     */
    @ResponseBody
    @GetMapping("/restaurants/{restIdx}/count")
    public BaseResponse<Integer> getReviewsCountByRest(@PathVariable("restIdx") int restIdx) {
        try {
            int count = reviewProvider.getReviewsCountByRest(restIdx);
            return new BaseResponse<>(count);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 가게의 별점 조회 API
     * [GET] /reviews/score/:restIdx
     */
    @ResponseBody
    @GetMapping("/score/{restIdx}")
    public BaseResponse<Double> getScore(@PathVariable("restIdx") int restIdx) {
        try {
            int count = reviewProvider.getReviewsCountByRest(restIdx);
            if(count == 0){
                return new BaseResponse<>(3.0);
            }
            else {
                double score = reviewProvider.getScore(restIdx);
                return new BaseResponse<>(score);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 리뷰변경 API
     * [PATCH] /reviews/:reviewIdx
     */
    @ResponseBody
    @PatchMapping("/{reviewIdx}")
    public BaseResponse<PatchReviewReq> modifyReview(@PathVariable("reviewIdx") int reviewIdx, @RequestBody PatchReviewReq patchReviewReq) {
        if (patchReviewReq.getScore() == null) {
            return new BaseResponse<>(PATCH_REVIEWS_EMPTY_SCORE);
        }
        else if (patchReviewReq.getScore() < 0 || patchReviewReq.getScore() > 5) {
            return new BaseResponse<>(PATCH_REVIEWS_INVALID_SCORE);
        }
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            int userIdx = reviewProvider.getUserIdxByReview(reviewIdx);
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            patchReviewReq.setReviewIdx(reviewIdx);
            reviewService.modifyReview(patchReviewReq);
            return new BaseResponse<>(patchReviewReq);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 리뷰삭제 API
     * [PATCH] /reviews/:reviewIdx/d
     */
    @ResponseBody
    @PatchMapping("/{reviewIdx}/d")
    public BaseResponse<Integer> deleteReview(@PathVariable("reviewIdx") int reviewIdx) {
        try {
            int reviewIdxByJwt = jwtService.getUserIdx();
            int userIdx = reviewProvider.getUserIdxByReview(reviewIdx);
            if(userIdx != reviewIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            reviewService.deleteReview(reviewIdx);
            return new BaseResponse<>(reviewIdx);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
