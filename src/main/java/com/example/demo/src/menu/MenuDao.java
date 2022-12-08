package com.example.demo.src.menu;

import com.example.demo.src.menu.model.PostMenuReq;
import com.example.demo.src.menu.model.PostMenuRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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

    //메뉴 생성
    public int createMenu(PostMenuReq postMenuReq) {

        String createMenuQuery = "insert into MENU (RESTAURANT_ID,GB_CODE,NAME,PRICE,CONTENT) VALUES (?,?,?,?,?)";

        Object[] createMenuParams = new Object[]{postMenuReq.getRestaurantId(), postMenuReq.getGbCode(), postMenuReq.getName()
                    , postMenuReq.getPrice(), postMenuReq.getContent()};

        this.jdbcTemplate.update(createMenuQuery, createMenuParams);

        String lastInsertIdQuery = "select last_insert_id()";

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);

    }

    public void createMenuImage(int menuIdx, PostMenuReq postMenuReq){


        String createRestaurantImageQuery = "insert into IMAGE (IMAGE_PATH, TARGET_ID, TARGET_CODE) values (?,?,'MN')";
        Object[] createRestaurantImageParams = new Object[]{postMenuReq.getMenuImage(),menuIdx};
        this.jdbcTemplate.update(createRestaurantImageQuery, createRestaurantImageParams);

    }

    //상세 메뉴 옵션 생성
    public int createMenuDetailOption(PostMenuReq postMenuReq){

            String createMenuQuery = "insert into MENU (RESTAURANT_ID,GB_CODE,NAME,PRICE,CONTENT,REF_ID,OPTION_ID) VALUES (?,?,?,?,?,?,?)";

            Object[] createMenuParams = new Object[]{postMenuReq.getRestaurantId(), postMenuReq.getGbCode(), postMenuReq.getName()
                    , postMenuReq.getPrice(), postMenuReq.getContent(), postMenuReq.getRefId(), postMenuReq.getOptionId()};

            this.jdbcTemplate.update(createMenuQuery, createMenuParams);

            String lastInsertIdQuery = "select last_insert_id()";

            return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);

    }

    public int modifyMenu(PostMenuReq postMenuReq) {

        String modifyMenuQuery = "update MENU set NAME = ?, PRICE = ?, CONTENT = ? where MENU_ID = ?";

        Object[] modifyMenuParams = new Object[]{postMenuReq.getName(), postMenuReq.getPrice(), postMenuReq.getContent()
                ,postMenuReq.getMenuId()};

        return this.jdbcTemplate.update(modifyMenuQuery, modifyMenuParams);
    }

    public int modifyMenuImage(PostMenuReq postMenuReq, int menuIdx){

        String modifyRestaurantImageQuery = "update IMAGE set IMAGE_PATH = ? where TARGET_ID = ? and TARGET_CODE = 'MN'";

        Object[] modifyRestaurantImageParams = new Object[]{postMenuReq.getMenuImage(),menuIdx};

        return this.jdbcTemplate.update(modifyRestaurantImageQuery,modifyRestaurantImageParams);
    }


    public int deleteMenu(int menuId) {
        String deletMenuQuery = "update MENU set DELETE_YN = 1 where MENU_ID = ? ";
        Object[] modifyUserNameParams = new Object[]{menuId};
        return this.jdbcTemplate.update(deletMenuQuery, modifyUserNameParams);
    }

//    public int deleteMenuImage(int menuId){
//
//        String deleteMenuImageQuery = "delete from IMAGE where TARGET_ID = ? and TARGET_CODE = 'MN'" ;
//        return this.jdbcTemplate.update(deleteMenuImageQuery,menuId);
//
//    }


    public PostMenuRes getMenuByMenuId(int menuId) {
        try {
            String getMenuQuery = "select MENU.*, IMAGE.IMAGE_PATH from MENU join IMAGE on MENU.MENU_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='MN' where MENU_ID = ?";

            return this.jdbcTemplate.queryForObject(getMenuQuery,
                    (rs, rowNum) -> new PostMenuRes(
                            rs.getInt("MENU_ID"),
                            rs.getInt("RESTAURANT_ID"),
                            rs.getString("GB_CODE"),
                            rs.getString("NAME"),
                            rs.getInt("PRICE"),
                            rs.getString("CONTENT"),
                            rs.getInt("REF_ID"),
                            rs.getString("IMAGE_PATH"),
                            rs.getInt("OPTION_ID")),
                    menuId);
        }catch (IncorrectResultSizeDataAccessException e){
            return null;
        }
    }

    public List<PostMenuRes> getAllMenus(int offset, int limit) {

        String getAllMenusQuery = "select MENU.*, IMAGE.IMAGE_PATH from MENU join IMAGE on MENU.MENU_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='MN' order by MENU.CREATION_DATE desc limit ?,?";

        return this.jdbcTemplate.query(getAllMenusQuery,
                (rs,rowNum) -> new PostMenuRes(
                        rs.getInt("MENU_ID"),
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("GB_CODE"),
                        rs.getString("NAME"),
                        rs.getInt("PRICE"),
                        rs.getString("CONTENT"),
                        rs.getInt("REF_ID"),
                        rs.getString("IMAGE_PATH"),
                        rs.getInt("OPTION_ID"))
        ,offset, limit);

    }

    public List<PostMenuRes> getRestaurantMenu(int restaurantId,int offset, int limit) {

        String getMenusQuery = "select MENU.*, IMAGE.IMAGE_PATH from MENU join IMAGE on MENU.MENU_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='MN' where RESTAURANT_ID = ? order by MENU.CREATION_DATE desc limit ?,?";

        return this.jdbcTemplate.query(getMenusQuery,
                (rs,rowNum) -> new PostMenuRes(
                        rs.getInt("MENU_ID"),
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("GB_CODE"),
                        rs.getString("NAME"),
                        rs.getInt("PRICE"),
                        rs.getString("CONTENT"),
                        rs.getInt("REF_ID"),
                        rs.getString("IMAGE_PATH"),
                        rs.getInt("OPTION_ID"))
                ,restaurantId,offset,limit);

    }

    public List<PostMenuRes> getMenusByName(String MenuName,int offset, int limit) {

        String getMenusQuery = "select MENU.*, IMAGE.IMAGE_PATH from MENU join IMAGE on MENU.MENU_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='MN' where MENU.NAME like ? order by MENU.CREATION_DATE desc limit ?,?";

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
                        rs.getString("IMAGE_PATH"),
                        rs.getInt("OPTION_ID"))
                ,Param,offset,limit);

    }

}
