package com.example.demo.src.menu_option;

import com.example.demo.src.menu_option.model.PostMenuOptionReq;
import com.example.demo.src.menu_option.model.PostMenuOptionRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MenuOptionDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createMenuOption(PostMenuOptionReq postMenuOptionReq){

        String createMenuOptionQuery = "insert into MENU_OPTION (MENU_ID,OPTION_NAME) VALUES (?,?)";

        Object[] createMenuParams = new Object[]{postMenuOptionReq.getMenuId(),postMenuOptionReq.getOptionName()};

        this.jdbcTemplate.update(createMenuOptionQuery, createMenuParams);

        String lastInsertIdQuery = "select last_insert_id()";

        System.out.println(lastInsertIdQuery);

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public PostMenuOptionRes getMenuOptionByMenuOptionId(int menuOptionId) {

        String getMenuQuery = "select * from MENU_OPTION where MENU_OPTION_ID = ?";

        return this.jdbcTemplate.queryForObject(getMenuQuery,
                (rs,rowNum) -> new PostMenuOptionRes(
                        rs.getInt("MENU_OPTION_ID"),
                        rs.getInt("MENU_ID"),
                        rs.getString("OPTION_NAME")),
                menuOptionId);
    }

    public int modifyMenuOption(PostMenuOptionReq postMenuOptionReq){

        String modifyMenuOptionMenu = "update MENU_OPTION set OPTION_NAME = ? where MENU_OPTION_ID = ?";

        Object[] modifyMenuOptionParams = new Object[]{postMenuOptionReq.getOptionName(),postMenuOptionReq.getMenuOptionId()};

        return this.jdbcTemplate.update(modifyMenuOptionMenu,modifyMenuOptionParams);
    }

    public int deleteMenuOption(int menuOptionId) {
        String deleteMenuOptionQuery = "update MENU_OPTION set DELETE_YN = 1 where MENU_OPTION_ID = ? ";
        Object[] modifyMenuOptionParams = new Object[]{menuOptionId};
        return this.jdbcTemplate.update(deleteMenuOptionQuery, modifyMenuOptionParams);
    }


    public List<PostMenuOptionRes> getAllMenuOptions() {

        String getAllMenusOptionQuery = "select * from MENU_OPTION";

        return this.jdbcTemplate.query(getAllMenusOptionQuery,
                (rs,rowNum) -> new PostMenuOptionRes(
                        rs.getInt("MENU_OPTION_ID"),
                        rs.getInt("MENU_ID"),
                        rs.getString("OPTION_NAME")));
    }

    public List<PostMenuOptionRes> getMenuOptionsByMenuId(int menuId) {

        String getMenuOptionsQuery = "select * from MENU_OPTION where MENU_ID = ?";

        return this.jdbcTemplate.query(getMenuOptionsQuery,
                (rs,rowNum) -> new PostMenuOptionRes(
                        rs.getInt("MENU_OPTION_ID"),
                        rs.getInt("MENU_ID"),
                        rs.getString("OPTION_NAME"))
        ,menuId);

    }
}
