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
        String createUserQuery = "insert into USER (NAME, EMAIL, PASSWORD, NICKNAME, PHONE_NUMBER, PROFILE_IMAGE, ADDRESS_ID, ADDRESS_DETAIL) values (?,?,?,?,?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getName(), postUserReq.getEmail(), postUserReq.getPassword(), postUserReq.getNickname(), postUserReq.getPhoneNumber(), postUserReq.getProfileImage(), postUserReq.getAddressId(), postUserReq.getAddressDetail()};
        
        System.out.println("확인2");
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
    public User loginUser(PostLoginReq postLoginReq) {
        String getPwdQuery = "select * from USER where EMAIL = ?";
        String getPwdParams = postLoginReq.getEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("USER_ID"),
                        rs.getString("NAME"),
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD"),
                        rs.getString("NICKNAME"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getInt("PROFILE_IMAGE"),
                        rs.getInt("ADDRESS_ID"),
                        rs.getString("ADDRESS_DETAIL"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getBoolean("MARKETING_AGREE_YN"),
                        rs.getBoolean("INFORM_NOTICE_AGREE_YN"),
                        rs.getBoolean("ORDER_NOTICE_AGREE_YN")
                ),
                getPwdParams
        );
    }

    // User 테이블에 존재하는 전체 유저들의 정보 조회
    public List<GetUserRes> getUsers() {
        String getUsersQuery = "select * from USER"; //User 테이블에 존재하는 모든 회원들의 정보를 조회하는 쿼리
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("USER_ID"),
                        rs.getString("NAME"),
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD"),
                        rs.getString("NICKNAME"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getInt("PROFILE_IMAGE"),
                        rs.getInt("ADDRESS_ID"),
                        rs.getString("ADDRESS_DETAIL"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getBoolean("MARKETING_AGREE_YN"),
                        rs.getBoolean("INFORM_NOTICE_AGREE_YN"),
                        rs.getBoolean("ORDER_NOTICE_AGREE_YN"))
        );
    }

    // 해당 userIdx를 갖는 유저조회
    public GetUserRes getUser(int userIdx) {
        String getUserQuery = "select * from USER where USER_ID = ?";
        int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("USER_ID"),
                        rs.getString("NAME"),
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD"),
                        rs.getString("NICKNAME"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getInt("PROFILE_IMAGE"),
                        rs.getInt("ADDRESS_ID"),
                        rs.getString("ADDRESS_DETAIL"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getBoolean("MARKETING_AGREE_YN"),
                        rs.getBoolean("INFORM_NOTICE_AGREE_YN"),
                        rs.getBoolean("ORDER_NOTICE_AGREE_YN")),
                getUserParams);
    }

    // 회원정보 변경
    public int modifyUserNickname(PatchUserReq patchUserReq) {
        String modifyUserNameQuery = "update USER set NICKNAME = ? where USER_ID = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getNickname(), patchUserReq.getUserIdx()};
        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams);
    }

    // 회원 삭제
    public int deleteUser(int userIdx) {
        String modifyUserNameQuery = "update USER set DELETE_YN = 1 where USER_ID = ? ";
        Object[] modifyUserNameParams = new Object[]{userIdx};
        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams);
    }
}
