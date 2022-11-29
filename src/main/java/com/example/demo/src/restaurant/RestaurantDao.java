package com.example.demo.src.restaurant;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.restaurant.model.GetRestaurantRes;
import com.example.demo.src.restaurant.model.PostRestaurantReq;
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

        String createRestaurantQuery = "insert into RESTAURANT (BUSINESS_NAME,REPRESENT_NAME,PHONE_NUMBER,OPERATING_TIME,TIP_DELIVERY,TIME_PICKUP,BUSINESS_NUMBER,CATEGORY,RESTAURANT_IMAGE,MINIMUM_ORDER_PRICE,DELIVERY_YN,ADDRESS_ID) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        Object[] createRestaurantParams = new Object[]{postRestaurantReq.getName(),postRestaurantReq.getRepresentName(), postRestaurantReq.getNumber(),postRestaurantReq.getOperationTime()
                ,postRestaurantReq.getTipDelivery(),postRestaurantReq.getTimeDelivery(),postRestaurantReq.getCompanyRegistrationNumber(),postRestaurantReq.getCategories()
                ,postRestaurantReq.getRestaurantImage(),postRestaurantReq.getMinDeliveryPrice()
                ,postRestaurantReq.getDeleteFlag()
                ,postRestaurantReq.getAddressId()};

        this.jdbcTemplate.update(createRestaurantQuery, createRestaurantParams);

        String lastInsertIdQuery = "select last_insert_id()";

        System.out.println(lastInsertIdQuery);

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int modifyRestaurant(PostRestaurantReq postRestaurantReq, int restaurantId){

        String modifyRestaurantQuery = "update RESTAURANT set BUSINESS_NAME = ? ,REPRESENT_NAME = ?, PHONE_NUMBER = ?, OPERATING_TIME = ?, TIP_DELIVERY = ?, TIME_PICKUP = ?, BUSINESS_NUMBER = ?,CATEGORY = ?," +
                " RESTAURANT_IMAGE = ?,  MINIMUM_ORDER_PRICE = ?,  DELIVERY_YN = ?, ADDRESS_ID =? where RESTAURANT_ID = ?";

        Object[] modifyRestaurantParams = new Object[]{postRestaurantReq.getName(),postRestaurantReq.getRepresentName(), postRestaurantReq.getNumber(),postRestaurantReq.getOperationTime()
                ,postRestaurantReq.getTipDelivery(),postRestaurantReq.getTimeDelivery(),postRestaurantReq.getCompanyRegistrationNumber(),postRestaurantReq.getCategories()
                ,postRestaurantReq.getRestaurantImage(),postRestaurantReq.getMinDeliveryPrice()
                ,postRestaurantReq.getDeleteFlag()
                ,postRestaurantReq.getAddressId(),restaurantId};

        for(int i = 0; i < modifyRestaurantParams.length; i++){
            System.out.println(modifyRestaurantParams[i]);
        }
        return this.jdbcTemplate.update(modifyRestaurantQuery,modifyRestaurantParams);
    }

    public int deleteRestaurant(int getRestaurantRes){
        String deleteRestaurantQuery = "delete from restaurant where restaurant_Id = ?";

        //int getRestaurantParams = getRestaurantRes.getRestaurantId();

        return this.jdbcTemplate.update(deleteRestaurantQuery,getRestaurantRes);
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

        String getRestaurantQuery = "select * from RESTAURANT";

        return this.jdbcTemplate.query(getRestaurantQuery,
                (rs,rowNum) -> new GetRestaurantRes(
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getString("OPERATING_TIME"),
                        rs.getString("INTRODUCTION_BOARD"),
                        rs.getString("TIP_DELIVERY"),
                        rs.getString("TIME_DELIVERY"),
                        rs.getString("BUSINESS_NUMBER"),
                        rs.getInt("CATEGORY"),
                        rs.getInt("RESTAURANT_IMAGE"),
                        rs.getString("MINIMUM_ORDER_PRICE"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getBoolean("DELIVERY_YN"),
                        rs.getBoolean("FAST_DELIVERY_YN"),
                        rs.getBoolean("PICKUP_YN"),
                        rs.getInt("ADDRESS_ID"),
                        rs.getString("ADDRESS_DETAIL"),
                        rs.getString("REPRESENT_NAME"),
                        rs.getString("ORIGIN_INFORMATION")));
    }

    public GetRestaurantRes getRestaurantByRestaurantId(int restaurantId) {

        String getRestaurantQuery = "select * from RESTAURANT where RESTAURANT_ID = ?";

        int getRestaurantParams = restaurantId;


        return this.jdbcTemplate.queryForObject(getRestaurantQuery,
                (rs,rowNum) -> new GetRestaurantRes(
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getString("OPERATING_TIME"),
                        rs.getString("INTRODUCTION_BOARD"),
                        rs.getString("TIP_DELIVERY"),
                        rs.getString("TIME_DELIVERY"),
                        rs.getString("BUSINESS_NUMBER"),
                        rs.getInt("CATEGORY"),
                        rs.getInt("RESTAURANT_IMAGE"),
                        rs.getString("MINIMUM_ORDER_PRICE"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getBoolean("DELIVERY_YN"),
                        rs.getBoolean("FAST_DELIVERY_YN"),
                        rs.getBoolean("PICKUP_YN"),
                        rs.getInt("ADDRESS_ID"),
                        rs.getString("ADDRESS_DETAIL"),
                        rs.getString("REPRESENT_NAME"),
                        rs.getString("ORIGIN_INFORMATION")),
                getRestaurantParams);
    }

    public List<GetRestaurantRes> getRestaurantsByNameSearch(String searchRestaurantNameReq) {

        String getRestaurantQuery = "select * from RESTAURANT where BUSINESS_NAME like ?";

        System.out.println(getRestaurantQuery);
        System.out.println(searchRestaurantNameReq);
        String Param = "%" + searchRestaurantNameReq + "%";
        System.out.println(Param);


        return this.jdbcTemplate.query(getRestaurantQuery,
                (rs,rowNum) -> new GetRestaurantRes(
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getString("OPERATING_TIME"),
                        rs.getString("INTRODUCTION_BOARD"),
                        rs.getString("TIP_DELIVERY"),
                        rs.getString("TIME_DELIVERY"),
                        rs.getString("BUSINESS_NUMBER"),
                        rs.getInt("CATEGORY"),
                        rs.getInt("RESTAURANT_IMAGE"),
                        rs.getString("MINIMUM_ORDER_PRICE"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getBoolean("DELIVERY_YN"),
                        rs.getBoolean("FAST_DELIVERY_YN"),
                        rs.getBoolean("PICKUP_YN"),
                        rs.getInt("ADDRESS_ID"),
                        rs.getString("ADDRESS_DETAIL"),
                        rs.getString("REPRESENT_NAME"),
                        rs.getString("ORIGIN_INFORMATION")),
                Param);
    }

    public List<GetRestaurantRes> getRestaurantsByCategorySearch(int searchRestaurantNameReq) {

        String getRestaurantQuery = "select * from RESTAURANT where CATEGORY like ?";

        System.out.println(getRestaurantQuery);
        System.out.println(searchRestaurantNameReq);
        String Param = "%" + searchRestaurantNameReq + "%";
        System.out.println(Param);


        return this.jdbcTemplate.query(getRestaurantQuery,
                (rs,rowNum) -> new GetRestaurantRes(
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getString("OPERATING_TIME"),
                        rs.getString("INTRODUCTION_BOARD"),
                        rs.getString("TIP_DELIVERY"),
                        rs.getString("TIME_DELIVERY"),
                        rs.getString("BUSINESS_NUMBER"),
                        rs.getInt("CATEGORY"),
                        rs.getInt("RESTAURANT_IMAGE"),
                        rs.getString("MINIMUM_ORDER_PRICE"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getBoolean("DELIVERY_YN"),
                        rs.getBoolean("FAST_DELIVERY_YN"),
                        rs.getBoolean("PICKUP_YN"),
                        rs.getInt("ADDRESS_ID"),
                        rs.getString("ADDRESS_DETAIL"),
                        rs.getString("REPRESENT_NAME"),
                        rs.getString("ORIGIN_INFORMATION")),
                Param);
    }
}
