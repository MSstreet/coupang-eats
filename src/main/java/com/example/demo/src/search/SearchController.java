package com.example.demo.src.search;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.search.model.GetSearchRes;
import com.example.demo.src.search.model.PostSearchReq;
import com.example.demo.src.search.model.PostSearchRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/searches")

public class SearchController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final SearchProvider searchProvider;
    @Autowired
    private final SearchService searchService;
    @Autowired
    private final JwtService jwtService;

    public SearchController(SearchProvider searchProvider, SearchService searchService, JwtService jwtService) {
        this.searchProvider = searchProvider;
        this.searchService = searchService;
        this.jwtService = jwtService;
    }

    /**
     * 검색어 등록 API
     * [POST] /searches
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostSearchRes> createSearch(@RequestBody PostSearchReq postSearchReq){
        try {
            PostSearchRes postSearchRes = searchService.createSearch(postSearchReq);
            return new BaseResponse<>(postSearchRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 모든 검색어 조회 API
     * [GET] /searches
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetSearchRes>> getSearches() {
        try {
            List<GetSearchRes> getSearchRes = searchProvider.getSearches();
            return new BaseResponse<>(getSearchRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 상위 10개 검색어 조회 API
     * [GET] /searches/top-ten
     */
    @ResponseBody
    @GetMapping("/top-ten")
    public BaseResponse<List<GetSearchRes>> getSearchesTopTen() {
        try {
            List<GetSearchRes> getSearchRes = searchProvider.getSearchesTopTen();
            return new BaseResponse<>(getSearchRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
