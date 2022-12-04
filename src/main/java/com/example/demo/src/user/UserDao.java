package com.example.demo.src.user;

import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository

public class UserDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 회원가입
    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into USER (NAME, EMAIL, PASSWORD, PHONE_NUMBER) values (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getName(), postUserReq.getEmail(), postUserReq.getPassword(), postUserReq.getPhoneNumber()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    // 이메일 확인
    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select EMAIL from USER where EMAIL = ?)";
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                email);
    }

    // 로그인: 해당 email에 해당되는 user 값을 가져온다.
    public GetUserRes loginUser(PostLoginReq postLoginReq) {
        String getPwdQuery = "select USER.* from USER " +
                "where USER.EMAIL = ?";
        String getPwdParams = postLoginReq.getEmail();
        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("USER_ID"),
                        rs.getString("NAME"),
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getBoolean("MARKETING_AGREE_YN"),
                        rs.getBoolean("INFORM_NOTICE_AGREE_YN"),
                        rs.getBoolean("ORDER_NOTICE_AGREE_YN")
                ),
                getPwdParams);
    }

    // User 테이블에 존재하는 전체 유저들의 정보 조회
    public List<GetUserRes> getUsers() {
        String getUsersQuery = "select USER.* from USER";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("USER_ID"),
                        rs.getString("NAME"),
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getBoolean("MARKETING_AGREE_YN"),
                        rs.getBoolean("INFORM_NOTICE_AGREE_YN"),
                        rs.getBoolean("ORDER_NOTICE_AGREE_YN"))
        );
    }

    // 해당 userIdx를 갖는 유저조회
    public GetUserRes getUser(int userIdx) {
        String getUserQuery =  "select USER.* from USER " +
                "where USER.USER_ID = ?";
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("USER_ID"),
                        rs.getString("NAME"),
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getBoolean("MARKETING_AGREE_YN"),
                        rs.getBoolean("INFORM_NOTICE_AGREE_YN"),
                        rs.getBoolean("ORDER_NOTICE_AGREE_YN")),
                userIdx);
    }

    // 회원정보 변경 - 이름, 이메일, 비밀번호, 휴대폰 번호, 주소 고유 번호, 상세 주소, 마케팅 동의 여부, 주문 알림 동의 여부
    public int modifyUser(PatchUserReq patchUserReq) {
        String modifyUserNameQuery = "update USER set NAME = ?, EMAIL = ?, PASSWORD = ?, PHONE_NUMBER = ?, " +
                "MARKETING_AGREE_YN = ?, INFORM_NOTICE_AGREE_YN = ?, ORDER_NOTICE_AGREE_YN = ? where USER_ID = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getName(), patchUserReq.getEmail(), patchUserReq.getPassword(), patchUserReq.getPhoneNumber(),
                patchUserReq.isMarketingAgreeYn(), patchUserReq.isInformNoticeAgreeYn(), patchUserReq.isOrderNoticeAgreeYn(), patchUserReq.getUserIdx()};
        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams);
    }

    // 회원 삭제
    public int deleteUser(int userIdx) {
        String modifyUserNameQuery = "update USER set DELETE_YN = 1 where USER_ID = ? ";
        Object[] modifyUserNameParams = new Object[]{userIdx};
        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams);
    }
}
