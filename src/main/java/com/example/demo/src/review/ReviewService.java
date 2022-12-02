package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.src.review.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ReviewService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ReviewDao reviewDao;
    private final ReviewProvider reviewProvider;
    private final JwtService jwtService;

    @Autowired
    public ReviewService(ReviewDao reviewDao, ReviewProvider reviewProvider, JwtService jwtService) {
        this.reviewDao = reviewDao;
        this.reviewProvider = reviewProvider;
        this.jwtService = jwtService;

    }

    // 리뷰 등록
    public PostReviewRes createReview(PostReviewReq postReviewReq) throws BaseException {
        if (reviewProvider.checkOrderIdx(postReviewReq.getOrderIdx()) == 1) {
            throw new BaseException(POST_REVIEWS_EXISTS_ORDER);
        }
        try {
            int reviewIdx = reviewDao.createReview(postReviewReq);
            if (postReviewReq.getReviewImagePath() != null || !postReviewReq.getReviewImagePath().isEmpty()) {
                reviewDao.createReviewImage(reviewIdx, postReviewReq);
            }
            return new PostReviewRes(reviewIdx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 리뷰 수정(Patch)
    public void modifyReview(PatchReviewReq patchReviewReq) throws BaseException {
        try {
            int resultModifyReview = reviewDao.modifyReview(patchReviewReq);
            int resultModifyImage = reviewDao.modifyImage(patchReviewReq);
            if (resultModifyReview == 0 || resultModifyImage == 0) {
                throw new BaseException(FAILED_TO_MODIFY);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 리뷰 삭제(Patch)
    public void deleteReview(int reviewIdx) throws BaseException {
        try {
            int result = reviewDao.deleteReview(reviewIdx);
            if (result == 0) {
                throw new BaseException(FAILED_TO_DELETE);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
