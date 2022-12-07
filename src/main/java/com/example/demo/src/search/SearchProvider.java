package com.example.demo.src.search;

import com.example.demo.config.BaseException;
import com.example.demo.src.search.model.GetSearchRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR_USER_ID;

@Service
public class SearchProvider {
    private final SearchDao searchDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SearchProvider(SearchDao searchDao, JwtService jwtService) {
        this.searchDao = searchDao;
        this.jwtService = jwtService;
    }


    // Searches 조회
    public List<GetSearchRes> getSearches() throws BaseException {
        try {
            List<GetSearchRes> getSearchRes = searchDao.getSearches();
            return getSearchRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // Searches top ten 조회
    public List<GetSearchRes> getSearchesTopTen() throws BaseException {
        try {
            List<GetSearchRes> getSearchRes = searchDao.getSearchesTopTen();
            return getSearchRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
