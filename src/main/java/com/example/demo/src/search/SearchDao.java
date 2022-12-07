package com.example.demo.src.search;

import com.example.demo.src.review.ReviewDao;
import com.example.demo.src.search.model.GetSearchRes;
import com.example.demo.src.search.model.PostSearchReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository

public class SearchDao {
    private ReviewDao reviewDao;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource, ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 검색 기록 등록
    public int createSearch(PostSearchReq postSearchReq) {
        String createSearchQuery = "insert into SEARCH (WORD, COUNT) values (?,?)";
        Object[] createSearchParams = new Object[]{postSearchReq.getWord(), 1};
        this.jdbcTemplate.update(createSearchQuery, createSearchParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public int getCount(String word) {
        String getCountQuery = "select COUNT from SEARCH where WORD = ?";
        try {
            return this.jdbcTemplate.queryForObject(getCountQuery, (rs, rowNum) -> rs.getInt("COUNT"), word);
        }
        catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public int plusCount(int count, PostSearchReq postSearchReq) {
        String plusCountQuery = "update SEARCH set COUNT = ? where WORD = ?";
        return this.jdbcTemplate.update(plusCountQuery, count+1, postSearchReq.getWord());
    }


    // Search 테이블에 존재하는 전체 검색 기록 조회
    public List<GetSearchRes> getSearches() {
        String getSearchesQuery = "select * from SEARCH order by COUNT desc";
        return this.jdbcTemplate.query(getSearchesQuery,
                (rs, rowNum) -> new GetSearchRes(
                        rs.getInt("SEARCH_ID"),
                        rs.getString("WORD"),
                        rs.getInt("COUNT"),
                        rs.getTimestamp("CREATION_DATE")
                )
        );
    }

    // 탑텐
    public List<GetSearchRes> getSearchesTopTen() {
        String getSearchesQuery = "select * from SEARCH order by COUNT desc limit 10";
        return this.jdbcTemplate.query(getSearchesQuery,
                (rs, rowNum) -> new GetSearchRes(
                        rs.getInt("SEARCH_ID"),
                        rs.getString("WORD"),
                        rs.getInt("COUNT"),
                        rs.getTimestamp("CREATION_DATE")
                )
        );
    }

}
