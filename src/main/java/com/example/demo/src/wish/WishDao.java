package com.example.demo.src.wish;


import com.example.demo.src.wish.model.GetWishRes;
import com.example.demo.src.wish.model.PatchWishReq;
import com.example.demo.src.wish.model.PostWishReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository

public class WishDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 찜 등록
    public int createWish(PostWishReq postWishReq) {
        String createWishQuery = "insert into WISH (USER_ID, RESTAURANT_ID) values (?,?)";
        Object[] createWishParams = new Object[]{postWishReq.getUserIdx(), postWishReq.getRestIdx()};
        this.jdbcTemplate.update(createWishQuery, createWishParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    // Wish 테이블에 존재하는 전체 찜 정보 조회
    public List<GetWishRes> getWishes() {
        String getWishesQuery = "select * from WISH";
        return this.jdbcTemplate.query(getWishesQuery,
                (rs, rowNum) -> new GetWishRes(
                        rs.getInt("WISH_ID"),
                        rs.getInt("USER_ID"),
                        rs.getInt("RESTAURANT_ID"),
                        rs.getBoolean("ACTIVE_YN")
                )
        );
    }

    // wish 하나 조회
    public GetWishRes getWish(int wishIdx) {
        String getWishsQuery = "select * from WISH where WISH_ID = ?";
        return this.jdbcTemplate.queryForObject(getWishsQuery,
                (rs, rowNum) -> new GetWishRes(
                        rs.getInt("WISH_ID"),
                        rs.getInt("USER_ID"),
                        rs.getInt("RESTAURANT_ID"),
                        rs.getBoolean("ACTIVE_YN")),
                wishIdx);
    }

    // 특정 user의 Wishes 조회
    public List<GetWishRes> getWishesByUser(int userIdx) {
        String getWishesQuery =  "select * from WISH where USER_ID = ? and ACTIVE_YN = true";
        return this.jdbcTemplate.query(getWishesQuery,
                (rs, rowNum) -> new GetWishRes(
                        rs.getInt("WISH_ID"),
                        rs.getInt("USER_ID"),
                        rs.getInt("RESTAURANT_ID"),
                        rs.getBoolean("ACTIVE_YN")),
                userIdx);
    }

    // 특정 user의 Wishes 수 조회
    public int getWishesCountByUser(int userIdx) {
        String getWishesQuery =  "select count(*) COUNT from WISH where USER_ID = ? and ACTIVE_YN = true";
        return this.jdbcTemplate.query(getWishesQuery, (rs, rowNum) -> rs.getInt("COUNT"), userIdx).get(0);
    }

    // 특정 가게의 Wishes 조회
    public List<GetWishRes> getWishesByRest(int restIdx) {
        String getWishesQuery =  "select * from WISH where RESTAURANT_ID = ? and ACTIVE_YN = true";
        return this.jdbcTemplate.query(getWishesQuery,
                (rs, rowNum) -> new GetWishRes(
                        rs.getInt("WISH_ID"),
                        rs.getInt("USER_ID"),
                        rs.getInt("RESTAURANT_ID"),
                        rs.getBoolean("ACTIVE_YN")),
                restIdx);
    }

    // 특정 가게의 Wishes 수 조회
    public Integer getWishesCountByRest(int restIdx) {
        String getWishesCountQuery =  "select count(*) as COUNT from WISH where RESTAURANT_ID = ? and ACTIVE_YN = true";
        return this.jdbcTemplate.query(getWishesCountQuery, (rs, rowNum) -> rs.getInt("COUNT"), restIdx).get(0);
    }

    // 찜 삭제
    public int deleteWish(int wishIdx) {
        String modifyWishQuery = "update WISH set ACTIVE_YN = 0 where WISH_ID = ? ";
        return this.jdbcTemplate.update(modifyWishQuery, wishIdx);
    }


    // 사용자 ID 조회
    public int getUserIdxByWish(int wishIdx){
        String getUserIdxQuery = "select USER_ID from WISH where WISH_ID = ?";
        int userIdx = this.jdbcTemplate.query(getUserIdxQuery, (rs, rowNum) -> rs.getInt("USER_ID"),wishIdx).get(0);
        return userIdx;

    }

}
