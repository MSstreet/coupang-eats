package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.src.review.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ReviewProvider {
    private final ReviewDao reviewDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ReviewProvider(ReviewDao reviewDao, JwtService jwtService) {
        this.reviewDao = reviewDao;
        this.jwtService = jwtService;
    }

    // 해당 주문의 리뷰가 있는지 확인
    public int checkOrderIdx(int orderIdx) throws BaseException {
        try {
            return reviewDao.checkOrderIdx(orderIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 주문자 ID 조회
    public int getUserIdxByOrder(int orderIdx) throws BaseException{
        try{
            int userIdx = reviewDao.getUserIdxByOrder(orderIdx);
            return userIdx;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR_USER_ID);
        }
    }

    // 리뷰 작성자 ID 조회
    public int getUserIdxByReview(int reviewIdx) throws BaseException{
        try{
            return reviewDao.getUserIdxByReview(reviewIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR_USER_ID);
        }
    }

    // Reviews 조회
    public List<GetReviewRes> getReviews(int offset, int limit) throws BaseException {
        try {
            List<GetReviewRes> getReviewRes = reviewDao.getReviews(offset, limit);
            return getReviewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // Review 하나 조회
    public GetReviewRes getReview(int reviewIdx) throws BaseException {
        try {
            GetReviewRes getReviewRes = reviewDao.getReview(reviewIdx);
            return getReviewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 특정 user의 Reviews 조회
    public List<GetReviewRes> getReviewsByUser(int userIdx, int offset, int limit) throws BaseException {
        try {
            List<GetReviewRes> getReviewRes = reviewDao.getReviewsByUser(userIdx, offset, limit);
            return getReviewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 특정 user의 Reviews 수 조회
    public int getReviewsCountByUser(int userIdx) throws BaseException {
        try {
            int count = reviewDao.getReviewsCountByUser(userIdx);
            return count;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 특정 가게의 Reviews 조회
    public List<GetReviewRes> getReviewsByRest(int restIdx, int offset, int limit) throws BaseException {
        try {
            List<GetReviewRes> getReviewRes = reviewDao.getReviewsByRest(restIdx,offset, limit);
            return getReviewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 특정 가게의 Reviews 수 조회
    public int getReviewsCountByRest(int restIdx) throws BaseException {
        try {
            int count = reviewDao.getReviewsCountByRest(restIdx);
            return count;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 특정 가게의 별점 평균 계산
    public double getScore(int restIdx) throws BaseException {
        try {
            double score = reviewDao.getScore(restIdx);
            return score;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
