package com.example.demo.src.Board;

import com.example.demo.src.Board.model.PostBoardReq;
import com.example.demo.src.Board.model.PostBoardRes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BoardDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //메뉴 생성
    public int createBoard(PostBoardReq postBoardReq) {

        String createBoardQuery = "insert into BOARD (TITLE,CONTENT) VALUES (?,?)";

        Object[] createBoardParams = new Object[]{postBoardReq.getTitle(),postBoardReq.getContent()};

        this.jdbcTemplate.update(createBoardQuery, createBoardParams);

        String lastInsertIdQuery = "select last_insert_id()";

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);

    }

    public PostBoardRes getBoardByBoardId(int boardId) {
        try {
            String getBoardQuery = "select * from BOARD where BOARD_ID = ? AND DELETE_YN = 0";

            return this.jdbcTemplate.queryForObject(getBoardQuery,
                    (rs, rowNum) -> new PostBoardRes(
                            rs.getInt("BOARD_ID"),
                            rs.getString("TITLE"),
                            rs.getString("CONTENT"),
                            rs.getBoolean("DELETE_YN")),
                    boardId);
        }catch (IncorrectResultSizeDataAccessException e){
            return null;
        }
    }

    public int modifyBoard(PostBoardReq postBoardReq, int boardIdx) {

        String modifyBoardQuery = "update BOARD set TITLE = ?, CONTENT = ? where BOARD_ID = ?";

        Object[] modifyBoardParams = new Object[]{postBoardReq.getTitle(), postBoardReq.getContent(),boardIdx};

        return this.jdbcTemplate.update(modifyBoardQuery, modifyBoardParams);
    }

    public int deleteBoard(int boardId) {
        String deleteBoardQuery = "update BOARD set DELETE_YN = 1 where BOARD_ID = ? ";
        Object[] modifyBoardParams = new Object[]{boardId};
        return this.jdbcTemplate.update(deleteBoardQuery, modifyBoardParams);
    }

    public List<PostBoardRes> getAllBoards() {

        String getAllMenusQuery = "select * from BOARD where DELETE_YN = 0";

        return this.jdbcTemplate.query(getAllMenusQuery,
                (rs,rowNum) -> new PostBoardRes(
                        rs.getInt("BOARD_ID"),
                        rs.getString("TITLE"),
                        rs.getString("CONTENT"),
                        rs.getBoolean("DELETE_YN")));

    }


}
