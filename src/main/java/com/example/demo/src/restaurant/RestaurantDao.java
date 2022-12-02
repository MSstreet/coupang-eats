package com.example.demo.src.restaurant;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.restaurant.model.GetRestaurantRes;
import com.example.demo.src.restaurant.model.PostRestaurantReq;
import com.example.demo.src.user.model.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@Repository
public class RestaurantDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createRestaurant(PostRestaurantReq postRestaurantReq){

        String createRestaurantQuery = "insert into RESTAURANT (BUSINESS_NAME, ADDRESS_ID, PHONE_NUMBER, REPRESENT_NAME, BUSINESS_NUMBER, OPERATING_TIME, TIP_DELIVERY, MINIMUM_ORDER_PRICE, CATEGORY, DELIVERY_YN, FAST_DELIVERY_YN, PICKUP_YN,DELETE_YN ) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Object[] createRestaurantParams = new Object[]{postRestaurantReq.getName(),postRestaurantReq.getAddressId(), postRestaurantReq.getNumber(),postRestaurantReq.getRepresentName()
                ,postRestaurantReq.getCompanyRegistrationNumber(),postRestaurantReq.getOperationTime(),postRestaurantReq.getTipDelivery(),postRestaurantReq.getMinDeliveryPrice()
                ,postRestaurantReq.getCategories(),postRestaurantReq.getDeliveryAvlb()
                ,postRestaurantReq.getFastDeliveryAvlb()
                ,postRestaurantReq.getPickupAvlb(),postRestaurantReq.getDeleteFlag()};

        this.jdbcTemplate.update(createRestaurantQuery, createRestaurantParams);

        String lastInsertIdQuery = "select last_insert_id()";

        System.out.println(lastInsertIdQuery);

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public void createRestaurantImage(int restaurantIdx, PostRestaurantReq postRestaurantReq){
        String createRestaurantImageQuery = "insert into IMAGE (IMAGE_PATH, TARGET_ID, TARGET_CODE) values (?,?,'RS')";
        Object[] createRestaurantImageParams = new Object[]{postRestaurantReq.getRestaurantImage(), restaurantIdx};
        this.jdbcTemplate.update(createRestaurantImageQuery, createRestaurantImageParams);
    }

    public int modifyRestaurant(PostRestaurantReq postRestaurantReq, int restaurantId){

        String modifyRestaurantQuery = "update RESTAURANT set BUSINESS_NAME = ? ,ADDRESS_ID = ?, PHONE_NUMBER = ?, REPRESENT_NAME = ?, BUSINESS_NUMBER = ?, OPERATING_TIME = ?, TIP_DELIVERY = ?,MINIMUM_ORDER_PRICE = ?," +
                " CATEGORY = ?,  DELIVERY_YN = ?,  FAST_DELIVERY_YN = ?, PICKUP_YN =? , DELETE_YN = ? where RESTAURANT_ID = ?";

        Object[] modifyRestaurantParams = new Object[]{postRestaurantReq.getName(),postRestaurantReq.getAddressId(), postRestaurantReq.getNumber(),postRestaurantReq.getRepresentName()
                ,postRestaurantReq.getCompanyRegistrationNumber(),postRestaurantReq.getOperationTime(),postRestaurantReq.getTipDelivery(),postRestaurantReq.getMinDeliveryPrice()
                ,postRestaurantReq.getCategories(),postRestaurantReq.getDeliveryAvlb()
                ,postRestaurantReq.getFastDeliveryAvlb()
                ,postRestaurantReq.getPickupAvlb(),postRestaurantReq.getDeleteFlag(),restaurantId};

        for(int i = 0; i < modifyRestaurantParams.length; i++){
            System.out.println(modifyRestaurantParams[i]);
        }
        return this.jdbcTemplate.update(modifyRestaurantQuery,modifyRestaurantParams);
    }

    public int modifyRestaurantImage(PostRestaurantReq postRestaurantReq, int restaurantId){
        String modifyRestaurantImageQuery = "update IMAGE set IMAGE_PATH = ? where TARGET_ID = ? and TARGET_CODE = 'RS'";

        Object[] modifyRestaurantImageParams = new Object[]{postRestaurantReq.getRestaurantImage(),restaurantId};

        return this.jdbcTemplate.update(modifyRestaurantImageQuery,modifyRestaurantImageParams);
    }


    public int deleteRestaurant(int restaurantId){
        String deleteRestaurantQuery = "update RESTAURANT set DELETE_YN = 1 where RESTAURANT_ID = ? ";

        return this.jdbcTemplate.update(deleteRestaurantQuery,restaurantId);
    }

    public int deleteRestaurantImage(int restaurantId){

        String deleteRestaurantImageQuery = "delete from IMAGE where TARGET_ID = ? and TARGET_CODE = 'RS'" ;
        return this.jdbcTemplate.update(deleteRestaurantImageQuery,restaurantId);

    }

    public int checkBusinessNum(String BusinessNum){
        String checkBusinessNumQuery = "select exists(select BUSINESS_NUMBER from RESTAURANT where BUSINESS_NUMBER = ?)";
        System.out.println("확인");
        System.out.println(BusinessNum);
        System.out.println(checkBusinessNumQuery);
        return this.jdbcTemplate.queryForObject(checkBusinessNumQuery,
                int.class,
                BusinessNum);

    }

    public List<GetRestaurantRes> getAllRestaurants(){

        String getRestaurantQuery = "select RESTAURANT.*, IMAGE.IMAGE_PATH from RESTAURANT " +
                "join IMAGE on RESTAURANT.RESTAURANT_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='RS'";

        return this.jdbcTemplate.query(getRestaurantQuery,
                (rs,rowNum) -> new GetRestaurantRes(
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getInt("ADDRESS_ID"),
                        rs.getString("ADDRESS_DETAIL"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getString("REPRESENT_NAME"),
                        rs.getString("BUSINESS_NUMBER"),
                        rs.getString("OPERATING_TIME"),
                        rs.getString("INTRODUCTION_BOARD"),
                        rs.getString("ORIGIN_INFORMATION"),
                        rs.getString("TIME_DELIVERY"),
                        rs.getString("TIME_PICKUP"),
                        rs.getString("TIP_DELIVERY"),
                        rs.getString("MINIMUM_ORDER_PRICE"),
                        rs.getInt("CATEGORY"),
                        rs.getBoolean("DELIVERY_YN"),
                        rs.getBoolean("FAST_DELIVERY_YN"),
                        rs.getBoolean("PICKUP_YN"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getString("IMAGE_PATH")));
    }

    public GetRestaurantRes getRestaurantByRestaurantId(int restaurantId) {

        String getRestaurantQuery = "select RESTAURANT.*, IMAGE.IMAGE_PATH from RESTAURANT  join IMAGE on RESTAURANT.RESTAURANT_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='RS' where RESTAURANT_ID = ?";

        int getRestaurantParams = restaurantId;

        return this.jdbcTemplate.queryForObject(getRestaurantQuery,
                (rs,rowNum) -> new GetRestaurantRes(
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getInt("ADDRESS_ID"),
                        rs.getString("ADDRESS_DETAIL"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getString("REPRESENT_NAME"),
                        rs.getString("BUSINESS_NUMBER"),
                        rs.getString("OPERATING_TIME"),
                        rs.getString("INTRODUCTION_BOARD"),
                        rs.getString("ORIGIN_INFORMATION"),
                        rs.getString("TIME_DELIVERY"),
                        rs.getString("TIME_PICKUP"),
                        rs.getString("TIP_DELIVERY"),
                        rs.getString("MINIMUM_ORDER_PRICE"),
                        rs.getInt("CATEGORY"),
                        rs.getBoolean("DELIVERY_YN"),
                        rs.getBoolean("FAST_DELIVERY_YN"),
                        rs.getBoolean("PICKUP_YN"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getString("IMAGE_PATH")),
                getRestaurantParams);
    }

    public List<GetRestaurantRes> getRestaurantsByNameSearch(String searchRestaurantNameReq) {

        String getRestaurantQuery = "select RESTAURANT.*, IMAGE.IMAGE_PATH from RESTAURANT  join IMAGE on RESTAURANT.RESTAURANT_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='RS' where BUSINESS_NAME like ?";

        System.out.println(getRestaurantQuery);
        System.out.println(searchRestaurantNameReq);
        String Param = "%" + searchRestaurantNameReq + "%";
        System.out.println(Param);


        return this.jdbcTemplate.query(getRestaurantQuery,
                (rs,rowNum) -> new GetRestaurantRes(
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getInt("ADDRESS_ID"),
                        rs.getString("ADDRESS_DETAIL"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getString("REPRESENT_NAME"),
                        rs.getString("BUSINESS_NUMBER"),
                        rs.getString("OPERATING_TIME"),
                        rs.getString("INTRODUCTION_BOARD"),
                        rs.getString("ORIGIN_INFORMATION"),
                        rs.getString("TIME_DELIVERY"),
                        rs.getString("TIME_PICKUP"),
                        rs.getString("TIP_DELIVERY"),
                        rs.getString("MINIMUM_ORDER_PRICE"),
                        rs.getInt("CATEGORY"),
                        rs.getBoolean("DELIVERY_YN"),
                        rs.getBoolean("FAST_DELIVERY_YN"),
                        rs.getBoolean("PICKUP_YN"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getString("IMAGE_PATH")),
                Param);
    }

    public List<GetRestaurantRes> getRestaurantsByCategorySearch(int searchRestaurantNameReq) {

        String getRestaurantQuery = "select RESTAURANT.*, IMAGE.IMAGE_PATH from RESTAURANT  join IMAGE on RESTAURANT.RESTAURANT_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='RS' where CATEGORY like ?";

        System.out.println(getRestaurantQuery);
        System.out.println(searchRestaurantNameReq);
        String Param = "%" + searchRestaurantNameReq + "%";
        System.out.println(Param);


        return this.jdbcTemplate.query(getRestaurantQuery,
                (rs,rowNum) -> new GetRestaurantRes(
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getInt("ADDRESS_ID"),
                        rs.getString("ADDRESS_DETAIL"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getString("REPRESENT_NAME"),
                        rs.getString("BUSINESS_NUMBER"),
                        rs.getString("OPERATING_TIME"),
                        rs.getString("INTRODUCTION_BOARD"),
                        rs.getString("ORIGIN_INFORMATION"),
                        rs.getString("TIME_DELIVERY"),
                        rs.getString("TIME_PICKUP"),
                        rs.getString("TIP_DELIVERY"),
                        rs.getString("MINIMUM_ORDER_PRICE"),
                        rs.getInt("CATEGORY"),
                        rs.getBoolean("DELIVERY_YN"),
                        rs.getBoolean("FAST_DELIVERY_YN"),
                        rs.getBoolean("PICKUP_YN"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getString("IMAGE_PATH")),
                Param);
    }
}
