package com.example.demo.src.review;


import com.example.demo.src.review.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository

public class ReviewDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 리뷰 등록
    public int createReview(PostReviewReq postReviewReq) {
        String createReviewQuery = "insert into REVIEW (ORDER_ID, SCORE, CONTENT, MENU_PUBLIC_YN, MENU_RECOMMEND_YN, OWNER_REPLY) values (?,?,?,?,?,?)";
        Object[] createReviewParams = new Object[]{postReviewReq.getOrderIdx(), postReviewReq.getScore(), postReviewReq.getContent(),
                postReviewReq.isMenuPublicYn(), postReviewReq.isMenuRecommendYn(), postReviewReq.getOwnerReply()};
        this.jdbcTemplate.update(createReviewQuery, createReviewParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }
    // 리뷰 이미지 생성
    public void createReviewImage(int reviewIdx, PostReviewReq postReviewReq){
        String createReviewQuery = "insert into IMAGE (IMAGE_PATH, TARGET_ID, TARGET_CODE) values (?,?,'RV')";
        Object[] createReviewParams = new Object[]{postReviewReq.getReviewImagePath(), reviewIdx};
        this.jdbcTemplate.update(createReviewQuery, createReviewParams);
    }

    // 해당 주문의 리뷰가 있는지 확인
    public int checkOrderIdx(int orderIdx) {
        String checkOrderIdxQuery = "select exists(select ORDER_ID from REVIEW where ORDER_ID = ?)";
        return this.jdbcTemplate.queryForObject(checkOrderIdxQuery, int.class, orderIdx);
    }

    // 리뷰 작성자 ID 조회
    public int getUserIdxByReview(int reviewIdx){

        String getUserIdxQuery = "select ORDERS.USER_ID from REVIEW join ORDERS on REVIEW.ORDER_ID = ORDERS.ORDER_ID where REVIEW.REVIEW_ID = ?";
        int userIdx = this.jdbcTemplate.query(getUserIdxQuery, (rs, rowNum) -> rs.getInt("USER_ID"),reviewIdx).get(0);
        return userIdx;
    }

    // 주문자 ID 조회
    public int getUserIdxByOrder(int orderIdx){
        String getUserIdxQuery = "select ORDERS.USER_ID from ORDERS where ORDERS.ORDER_ID = ?";
        int userIdx = this.jdbcTemplate.query(getUserIdxQuery, (rs, rowNum) -> rs.getInt("USER_ID"),orderIdx).get(0);
        return userIdx;
    }

    // Review 테이블에 존재하는 전체 리뷰 정보 조회
    public List<GetReviewRes> getReviews() {
        String getReviewsQuery = "select REVIEW.*, IMAGE.IMAGE_PATH, ORDERS.USER_ID, ORDERS.RESTAURANT_ID, REST.BUSINESS_NAME from REVIEW " +
                "left join IMAGE on REVIEW.REVIEW_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='RV' " +
                "left join ORDERS on REVIEW.ORDER_ID = ORDERS.ORDER_ID " +
                "left join RESTAURANT REST on REST.RESTAURANT_ID = ORDERS.RESTAURANT_ID " +
                "order by REVIEW.CREATION_DATE desc";
        return this.jdbcTemplate.query(getReviewsQuery,
                (rs, rowNum) -> new GetReviewRes(
                        rs.getInt("REVIEW_ID"),
                        rs.getInt("ORDER_ID"),
                        rs.getInt("USER_ID"),
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getInt("SCORE"),
                        rs.getString("CONTENT"),
                        rs.getBoolean("MENU_PUBLIC_YN"),
                        rs.getBoolean("MENU_RECOMMEND_YN"),
                        rs.getString("OWNER_REPLY"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getString("IMAGE_PATH")
                )
        );
    }

    // review 하나 조회
    public GetReviewRes getReview(int reviewIdx) {
        String getReviewsQuery = "select REVIEW.*, IMAGE.IMAGE_PATH, ORDERS.USER_ID, ORDERS.RESTAURANT_ID, REST.BUSINESS_NAME from REVIEW " +
                "left join IMAGE on REVIEW.REVIEW_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='RV' " +
                "left join ORDERS on REVIEW.ORDER_ID = ORDERS.ORDER_ID " +
                "left join RESTAURANT REST on REST.RESTAURANT_ID = ORDERS.RESTAURANT_ID " +
                "where REVIEW.REVIEW_ID = ?";
        return this.jdbcTemplate.queryForObject(getReviewsQuery,
                (rs, rowNum) -> new GetReviewRes(
                        rs.getInt("REVIEW_ID"),
                        rs.getInt("ORDER_ID"),
                        rs.getInt("USER_ID"),
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getInt("SCORE"),
                        rs.getString("CONTENT"),
                        rs.getBoolean("MENU_PUBLIC_YN"),
                        rs.getBoolean("MENU_RECOMMEND_YN"),
                        rs.getString("OWNER_REPLY"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getString("IMAGE_PATH")),
                reviewIdx);
    }

    // 특정 user의 Reviews 조회
    public List<GetReviewRes> getReviewsByUser(int userIdx) {
        String getReviewsQuery =  "select REVIEW.*, IMAGE.IMAGE_PATH, ORDERS.USER_ID, ORDERS.RESTAURANT_ID, REST.BUSINESS_NAME from REVIEW " +
                "left join IMAGE on REVIEW.REVIEW_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='RV' " +
                "left join ORDERS on REVIEW.ORDER_ID = ORDERS.ORDER_ID " +
                "left join RESTAURANT REST on REST.RESTAURANT_ID = ORDERS.RESTAURANT_ID " +
                "where ORDERS.USER_ID = ? and REVIEW.DELETE_YN = false " +
                "order by REVIEW.CREATION_DATE desc";
        return this.jdbcTemplate.query(getReviewsQuery,
                (rs, rowNum) -> new GetReviewRes(
                        rs.getInt("REVIEW_ID"),
                        rs.getInt("ORDER_ID"),
                        rs.getInt("USER_ID"),
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getInt("SCORE"),
                        rs.getString("CONTENT"),
                        rs.getBoolean("MENU_PUBLIC_YN"),
                        rs.getBoolean("MENU_RECOMMEND_YN"),
                        rs.getString("OWNER_REPLY"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getString("IMAGE_PATH")),
                userIdx);
    }

    // 특정 user의 Reviews 수 조회
    public int getReviewsCountByUser(int userIdx) {
        String getReviewsQuery =  "select count(*) as COUNT from REVIEW join ORDERS on REVIEW.ORDER_ID = ORDERS.ORDER_ID " +
                "where ORDERS.USER_ID = ? and REVIEW.DELETE_YN = false";
        return this.jdbcTemplate.query(getReviewsQuery, (rs, rowNum) -> rs.getInt("COUNT"), userIdx).get(0);
    }

    // 특정 가게의 Reviews 조회
    public List<GetReviewRes> getReviewsByRest(int restIdx) {
        String getReviewsQuery =  "select REVIEW.*, IMAGE.IMAGE_PATH, ORDERS.USER_ID, ORDERS.RESTAURANT_ID, REST.BUSINESS_NAME from REVIEW " +
                "left join IMAGE on REVIEW.REVIEW_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='RV' " +
                "left join ORDERS on REVIEW.ORDER_ID = ORDERS.ORDER_ID " +
                "left join RESTAURANT REST on REST.RESTAURANT_ID = ORDERS.RESTAURANT_ID " +
                "where ORDERS.RESTAURANT_ID = ? and REVIEW.DELETE_YN = false " +
                "order by REVIEW.CREATION_DATE desc";
        return this.jdbcTemplate.query(getReviewsQuery,
                (rs, rowNum) -> new GetReviewRes(
                        rs.getInt("REVIEW_ID"),
                        rs.getInt("ORDER_ID"),
                        rs.getInt("USER_ID"),
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getInt("SCORE"),
                        rs.getString("CONTENT"),
                        rs.getBoolean("MENU_PUBLIC_YN"),
                        rs.getBoolean("MENU_RECOMMEND_YN"),
                        rs.getString("OWNER_REPLY"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getString("IMAGE_PATH")),
                restIdx);
    }

    // 특정 가게의 Reviews 수 조회
    public int getReviewsCountByRest(int restIdx) {
        String getReviewsQuery =  "select count(*) as COUNT from REVIEW join ORDERS on REVIEW.ORDER_ID = ORDERS.ORDER_ID " +
                "where ORDERS.RESTAURANT_ID = ? and REVIEW.DELETE_YN = false";
        return this.jdbcTemplate.query(getReviewsQuery, (rs, rowNum) -> rs.getInt("COUNT"), restIdx).get(0);
    }

    // 특정 가게의 별점 평균 계산
    public double getScore(int restIdx){
        String getScoreQuery = "select avg(REVIEW.SCORE) as SCORE from REVIEW " +
                "join ORDERS on REVIEW.ORDER_ID = ORDERS.ORDER_ID " +
                "where ORDERS.RESTAURANT_ID = ? and REVIEW.DELETE_YN = 0";
        double score = this.jdbcTemplate.query(getScoreQuery, (rs, rowNum) -> rs.getFloat("SCORE"), restIdx).get(0);
        return score;
    }
    // 리뷰 변경 - 별점, 내용, 메뉴 공개 여부, 메뉴 추천 여부, 사장님 답글, 삭제 여부
    public int modifyReview(PatchReviewReq patchReviewReq) {
        String modifyReviewQuery = "update REVIEW set SCORE = ?, CONTENT = ?, MENU_PUBLIC_YN = ?, MENU_RECOMMEND_YN = ?, OWNER_REPLY = ?" +
                "where REVIEW_ID = ?";
        Object[] modifyReviewParams = new Object[]{patchReviewReq.getScore(), patchReviewReq.getContent(), patchReviewReq.isMenuPublicYn(),
                patchReviewReq.isMenuRecommendYn(), patchReviewReq.getOwnerReply(), patchReviewReq.getReviewIdx()};
        return this.jdbcTemplate.update(modifyReviewQuery, modifyReviewParams);
    }

    // 리뷰 변경 - 이미지
    public int modifyImage(PatchReviewReq patchReviewReq) {
        String modifyImageQuery = "update IMAGE set IMAGE_PATH = ? where TARGET_ID = ? and TARGET_CODE = 'RV' ";
        Object[] modifyImageParams = new Object[]{patchReviewReq.getReviewImagePath(), patchReviewReq.getReviewIdx()};
        return this.jdbcTemplate.update(modifyImageQuery, modifyImageParams);
    }

    // 리뷰 삭제
    public int deleteReview(int reviewIdx) {
        String modifyReviewNameQuery = "update REVIEW set DELETE_YN = 1 where REVIEW_ID = ? ";
        return this.jdbcTemplate.update(modifyReviewNameQuery, reviewIdx);
    }
}
