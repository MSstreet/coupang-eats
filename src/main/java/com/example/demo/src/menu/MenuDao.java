package com.example.demo.src.menu;

import com.example.demo.src.menu.model.PostMenuReq;
import com.example.demo.src.menu.model.PostMenuRes;
import com.example.demo.src.restaurant.model.GetRestaurantRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MenuDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createMenu(PostMenuReq postMenuReq) {

        if(postMenuReq.getGbCode().equals("메뉴")) {

            String createMenuQuery = "insert into MENU (RESTAURANT_ID,GB_CODE,NAME,PRICE,CONTENT) VALUES (?,?,?,?,?)";

            Object[] createMenuParams = new Object[]{postMenuReq.getRestaurantId(), postMenuReq.getGbCode(), postMenuReq.getName()
                    , postMenuReq.getPrice(), postMenuReq.getContent()};

            this.jdbcTemplate.update(createMenuQuery, createMenuParams);

            String lastInsertIdQuery = "select last_insert_id()";

            return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
        }else{

            String createMenuQuery = "insert into MENU (RESTAURANT_ID,GB_CODE,NAME,PRICE,CONTENT,REF_ID,OPTION_ID) VALUES (?,?,?,?,?,?,?)";

            Object[] createMenuParams = new Object[]{postMenuReq.getRestaurantId(), postMenuReq.getGbCode(), postMenuReq.getName()
                    , postMenuReq.getPrice(), postMenuReq.getContent(), postMenuReq.getRefId(), postMenuReq.getOptionId()};

            this.jdbcTemplate.update(createMenuQuery, createMenuParams);

            String lastInsertIdQuery = "select last_insert_id()";

            return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
        }

    }

    public int modifyMenu(PostMenuReq postMenuReq) {

        String modifyMenuQuery = "update MENU set NAME = ?, PRICE = ?, CONTENT = ? where menu_Id = ?";

        Object[] modifyMenuParams = new Object[]{postMenuReq.getName(), postMenuReq.getPrice(), postMenuReq.getContent()
                ,postMenuReq.getMenuId()};

        return this.jdbcTemplate.update(modifyMenuQuery, modifyMenuParams);
    }

    public int deleteMenu(int menuId) {
        String deletMenuQuery = "update MENU set DELETE_YN = 1 where MENU_ID = ? ";
        Object[] modifyUserNameParams = new Object[]{menuId};
        return this.jdbcTemplate.update(deletMenuQuery, modifyUserNameParams);
    }


    public PostMenuRes getMenuByMenuId(int menuId) {

        String getMenuQuery = "select * from MENU where MENU_ID = ?";

        return this.jdbcTemplate.queryForObject(getMenuQuery,
                (rs,rowNum) -> new PostMenuRes(
                        rs.getInt("MENU_ID"),
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("GB_CODE"),
                        rs.getString("NAME"),
                        rs.getInt("PRICE"),
                        rs.getString("CONTENT"),
                        rs.getInt("REF_ID"),
                        rs.getInt("OPTION_ID")),
                menuId);
    }

    public List<PostMenuRes> getAllMenus() {

        String getAllMenusQuery = "select * from MENU";

        return this.jdbcTemplate.query(getAllMenusQuery,
                (rs,rowNum) -> new PostMenuRes(
                        rs.getInt("MENU_ID"),
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("GB_CODE"),
                        rs.getString("NAME"),
                        rs.getInt("PRICE"),
                        rs.getString("CONTENT"),
                        rs.getInt("REF_ID"),
                        rs.getInt("OPTION_ID")));

    }

    public List<PostMenuRes> getRestaurantMenu(int restaurantId) {

        String getMenusQuery = "select * from MENU where RESTAURANT_ID = ?";

        return this.jdbcTemplate.query(getMenusQuery,
                (rs,rowNum) -> new PostMenuRes(
                        rs.getInt("MENU_ID"),
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("GB_CODE"),
                        rs.getString("NAME"),
                        rs.getInt("PRICE"),
                        rs.getString("CONTENT"),
                        rs.getInt("REF_ID"),
                        rs.getInt("OPTION_ID"))
                ,restaurantId);

    }

    public List<PostMenuRes> getMenusByName(String MenuName) {

        String getMenusQuery = "select * from MENU where NAME like ?";

        String Param = "%" + MenuName + "%";

        return this.jdbcTemplate.query(getMenusQuery,
                (rs,rowNum) -> new PostMenuRes(
                        rs.getInt("MENU_ID"),
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("GB_CODE"),
                        rs.getString("NAME"),
                        rs.getInt("PRICE"),
                        rs.getString("CONTENT"),
                        rs.getInt("REF_ID"),
                        rs.getInt("OPTION_ID"))
                ,MenuName);

    }


}
