package com.example.demo.src.wish;

import com.example.demo.config.BaseException;
import com.example.demo.src.wish.model.PatchWishReq;
import com.example.demo.src.wish.model.PostWishReq;
import com.example.demo.src.wish.model.PostWishRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class WishService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final WishDao wishDao;
    private final WishProvider wishProvider;
    private final JwtService jwtService;

    @Autowired
    public WishService(WishDao wishDao, WishProvider wishProvider, JwtService jwtService) {
        this.wishDao = wishDao;
        this.wishProvider = wishProvider;
        this.jwtService = jwtService;

    }

    // 찜 등록
    public PostWishRes createWish(PostWishReq postWishReq) throws BaseException {
        try {
            int wishIdx;
            if (wishProvider.checkWishIdx(postWishReq.getUserIdx(), postWishReq.getRestIdx()) == 1) {
                wishIdx = wishDao.reCreateWish(postWishReq.getUserIdx(), postWishReq.getRestIdx());
            }
            else {
                wishIdx = wishDao.createWish(postWishReq);
            }
            return new PostWishRes(wishIdx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 찜 삭제(Patch)
    public void deleteWish(int wishIdx) throws BaseException {
        try {
            int result = wishDao.deleteWish(wishIdx);
            if (result == 0) {
                throw new BaseException(FAILED_TO_DELETE);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
