package com.example.demo.src.wish;

import com.example.demo.config.BaseException;
import com.example.demo.src.wish.model.GetWishRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR_USER_ID;

@Service
public class WishProvider {
    private final WishDao wishDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public WishProvider(WishDao wishDao, JwtService jwtService) {
        this.wishDao = wishDao;
        this.jwtService = jwtService;
    }

    // 찜이 이미 있는지 확인
    public int checkWishIdx(int userIdx, int restIdx) throws BaseException {
        try {
            return wishDao.checkWishIdx(userIdx, restIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // Wishes 조회
    public List<GetWishRes> getWishes(int offset, int limit) throws BaseException {
        try {
            List<GetWishRes> getWishRes = wishDao.getWishes(offset, limit);
            return getWishRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // Wish 하나 조회
    public GetWishRes getWish(int wishIdx) throws BaseException {
        try {
            GetWishRes getWishRes = wishDao.getWish(wishIdx);
            return getWishRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 특정 user의 Wishes 조회
    public List<GetWishRes> getWishesByUser(int userIdx, int offset, int limit) throws BaseException {
        try {
            List<GetWishRes> getWishRes = wishDao.getWishesByUser(userIdx, offset, limit);
            return getWishRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 특정 user의 Wishes 수 조회
    public int getWishesCountByUser(int userIdx) throws BaseException {
        try {
            int count = wishDao.getWishesCountByUser(userIdx);
            return count;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 특정 가게의 Wishes 수 조회
    public int getWishesCountByRest(int restIdx) throws BaseException {
        try {
            int count = wishDao.getWishesCountByRest(restIdx);
            return count;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int getUserIdxByWish(int wishIdx) throws BaseException{
        try {
            int userIdx = wishDao.getUserIdxByWish(wishIdx);
            return userIdx;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR_USER_ID);
        }
    }
}
