package com.example.demo.src.search;

import com.example.demo.config.BaseException;
import com.example.demo.src.search.model.PostSearchReq;
import com.example.demo.src.search.model.PostSearchRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.FAILED_TO_DELETE;

@Service
public class SearchService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SearchDao searchDao;
    private final SearchProvider searchProvider;
    private final JwtService jwtService;

    @Autowired
    public SearchService(SearchDao searchDao, SearchProvider searchProvider, JwtService jwtService) {
        this.searchDao = searchDao;
        this.searchProvider = searchProvider;
        this.jwtService = jwtService;

    }

    // 검색 기록 등록
    public PostSearchRes createSearch(PostSearchReq postSearchReq) throws BaseException {
        try {
            int searchIdx;
            int count = searchDao.getCount(postSearchReq.getWord());
            if(count > 0) {
                searchIdx = searchDao.plusCount(count, postSearchReq);
            }
            else{
                searchIdx = searchDao.createSearch(postSearchReq);
            }
            return new PostSearchRes(searchIdx, count+1);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
