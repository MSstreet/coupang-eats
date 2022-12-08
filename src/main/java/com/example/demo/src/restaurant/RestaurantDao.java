package com.example.demo.src.restaurant;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.restaurant.model.GetRestaurantRes;
import com.example.demo.src.restaurant.model.PostRestaurantReq;
import com.example.demo.src.user.model.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Repository
public class RestaurantDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createRestaurant(PostRestaurantReq postRestaurantReq){

        String createRestaurantQuery = "insert into RESTAURANT (BUSINESS_NAME, ADDRESS, PHONE_NUMBER, REPRESENT_NAME, BUSINESS_NUMBER, OPERATING_TIME, TIP_DELIVERY,TIME_DELIVERY, MINIMUM_ORDER_PRICE, CATEGORY, DELIVERY_YN, FAST_DELIVERY_YN, PICKUP_YN,DELETE_YN,DISTANCE ) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Object[] createRestaurantParams = new Object[]{postRestaurantReq.getName(),postRestaurantReq.getAddress(), postRestaurantReq.getNumber(),postRestaurantReq.getRepresentName()
                ,postRestaurantReq.getCompanyRegistrationNumber(),postRestaurantReq.getOperationTime(),postRestaurantReq.getTipDelivery(),postRestaurantReq.getTimeDelivery(),postRestaurantReq.getMinDeliveryPrice()
                ,postRestaurantReq.getCategories(),postRestaurantReq.getDeliveryAvlb()
                ,postRestaurantReq.getFastDeliveryAvlb()
                ,postRestaurantReq.getPickupAvlb(),postRestaurantReq.getDeleteFlag(),postRestaurantReq.getDistance()};

        this.jdbcTemplate.update(createRestaurantQuery, createRestaurantParams);

        String lastInsertIdQuery = "select last_insert_id()";

        System.out.println(lastInsertIdQuery);

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public void createRestaurantImage(int restaurantIdx, PostRestaurantReq postRestaurantReq){

        //if(check == 0) {

            System.out.println("dao 확인 3");

            String createRestaurantImageQuery = "insert into IMAGE (IMAGE_PATH, IMAGE_PATH2, IMAGE_PATH3, TARGET_ID, TARGET_CODE) values (?,?,?,?,'RS')";
            Object[] createRestaurantImageParams = new Object[]{postRestaurantReq.getRestaurantImage(),postRestaurantReq.getRestaurantImage1(),postRestaurantReq.getRestaurantImage2(), restaurantIdx};
            this.jdbcTemplate.update(createRestaurantImageQuery, createRestaurantImageParams);

        //}

//        }else if(check == 1){
//
//            String createRestaurantImageQuery = "insert into IMAGE (IMAGE_PATH2, TARGET_ID, TARGET_CODE) values (?,?,'RS')";
//            Object[] createRestaurantImageParams = new Object[]{postRestaurantReq.getRestaurantImage1(), restaurantIdx};
//            this.jdbcTemplate.update(createRestaurantImageQuery, createRestaurantImageParams);
//
//        }else{
//
//            String createRestaurantImageQuery = "insert into IMAGE (IMAGE_PATH3, TARGET_ID, TARGET_CODE) values (?,?,'RS')";
//            Object[] createRestaurantImageParams = new Object[]{postRestaurantReq.getRestaurantImage2(), restaurantIdx};
//            this.jdbcTemplate.update(createRestaurantImageQuery, createRestaurantImageParams);
//
//        }
    }

    public int modifyRestaurant(PostRestaurantReq postRestaurantReq, int restaurantId){

        String modifyRestaurantQuery = "update RESTAURANT set BUSINESS_NAME = ? ,ADDRESS = ?, PHONE_NUMBER = ?, REPRESENT_NAME = ?, BUSINESS_NUMBER = ?, OPERATING_TIME = ?, TIP_DELIVERY = ?,MINIMUM_ORDER_PRICE = ?," +
                " CATEGORY = ?,  DELIVERY_YN = ?,  FAST_DELIVERY_YN = ?, PICKUP_YN =? , DELETE_YN = ?, DISTANCE = ? where RESTAURANT_ID = ?";

        Object[] modifyRestaurantParams = new Object[]{postRestaurantReq.getName(),postRestaurantReq.getAddress(), postRestaurantReq.getNumber(),postRestaurantReq.getRepresentName()
                ,postRestaurantReq.getCompanyRegistrationNumber(),postRestaurantReq.getOperationTime(),postRestaurantReq.getTipDelivery(),postRestaurantReq.getMinDeliveryPrice()
                ,postRestaurantReq.getCategories(),postRestaurantReq.getDeliveryAvlb()
                ,postRestaurantReq.getFastDeliveryAvlb()
                ,postRestaurantReq.getPickupAvlb(),postRestaurantReq.getDeleteFlag(),postRestaurantReq.getDistance(),restaurantId };

//        for(int i = 0; i < modifyRestaurantParams.length; i++){
//            System.out.println(modifyRestaurantParams[i]);
//        }

        return this.jdbcTemplate.update(modifyRestaurantQuery,modifyRestaurantParams);
    }

    public int modifyRestaurantImage(PostRestaurantReq postRestaurantReq, int restaurantId){
        System.out.println("dao 확인!!!");

        String modifyRestaurantImageQuery = "update IMAGE set IMAGE_PATH = ?, IMAGE_PATH2 = ?, IMAGE_PATH3 = ? where TARGET_ID = ? and TARGET_CODE = 'RS'";

        Object[] modifyRestaurantImageParams = new Object[]{postRestaurantReq.getRestaurantImage(),postRestaurantReq.getRestaurantImage1(),postRestaurantReq.getRestaurantImage2(),restaurantId};


        for(int i = 0; i < modifyRestaurantImageParams.length; i++){
            System.out.println(modifyRestaurantImageParams[i]);
        }

        return this.jdbcTemplate.update(modifyRestaurantImageQuery,modifyRestaurantImageParams);
    }


    public int deleteRestaurant(int restaurantId){
        String deleteRestaurantQuery = "update RESTAURANT set DELETE_YN = 1 where RESTAURANT_ID = ? ";

        return this.jdbcTemplate.update(deleteRestaurantQuery,restaurantId);
    }

//    public int deleteRestaurantImage(int restaurantId){
//
//        String deleteRestaurantImageQuery = "delete from IMAGE where TARGET_ID = ? and TARGET_CODE = 'RS'" ;
//        return this.jdbcTemplate.update(deleteRestaurantImageQuery,restaurantId);
//
//    }



    public List<GetRestaurantRes> getAllRestaurants(int offset, int limit){

        String getRestaurantQuery = "select RESTAURANT.*, IMAGE.IMAGE_PATH, IMAGE.IMAGE_PATH2, IMAGE.IMAGE_PATH3 from RESTAURANT " +
                "join IMAGE on RESTAURANT.RESTAURANT_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='RS' order by RESTAURANT.CREATION_DATE desc limit ?,?";
        return this.jdbcTemplate.query(getRestaurantQuery,
                (rs,rowNum) -> new GetRestaurantRes(
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getString("ADDRESS"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getString("REPRESENT_NAME"),
                        rs.getString("BUSINESS_NUMBER"),
                        rs.getString("OPERATING_TIME"),
                        rs.getString("INTRODUCTION_BOARD"),
                        rs.getString("ORIGIN_INFORMATION"),
                        rs.getInt("TIME_DELIVERY"),
                        rs.getInt("TIME_PICKUP"),
                        rs.getString("TIP_DELIVERY"),
                        rs.getString("MINIMUM_ORDER_PRICE"),
                        rs.getString("CATEGORY"),
                        rs.getBoolean("DELIVERY_YN"),
                        rs.getBoolean("FAST_DELIVERY_YN"),
                        rs.getBoolean("PICKUP_YN"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getString("IMAGE_PATH"),
                        rs.getString("IMAGE_PATH2"),
                        rs.getString("IMAGE_PATH3"),
                        rs.getDouble("SCORE"),
                        rs.getDouble("DISTANCE")),
                offset, limit);
    }

    public GetRestaurantRes getRestaurantByRestaurantId(int restaurantId) throws Exception {
        try {
            String getRestaurantQuery = "select RESTAURANT.*, IMAGE.IMAGE_PATH, IMAGE.IMAGE_PATH2, IMAGE.IMAGE_PATH3 from RESTAURANT  join IMAGE on RESTAURANT.RESTAURANT_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='RS' where RESTAURANT_ID = ?";

            int getRestaurantParams = restaurantId;

            return this.jdbcTemplate.queryForObject(getRestaurantQuery,
                    (rs, rowNum) -> new GetRestaurantRes(
                            rs.getInt("RESTAURANT_ID"),
                            rs.getString("BUSINESS_NAME"),
                            rs.getString("ADDRESS"),
                            rs.getString("PHONE_NUMBER"),
                            rs.getString("REPRESENT_NAME"),
                            rs.getString("BUSINESS_NUMBER"),
                            rs.getString("OPERATING_TIME"),
                            rs.getString("INTRODUCTION_BOARD"),
                            rs.getString("ORIGIN_INFORMATION"),
                            rs.getInt("TIME_DELIVERY"),
                            rs.getInt("TIME_PICKUP"),
                            rs.getString("TIP_DELIVERY"),
                            rs.getString("MINIMUM_ORDER_PRICE"),
                            rs.getString("CATEGORY"),
                            rs.getBoolean("DELIVERY_YN"),
                            rs.getBoolean("FAST_DELIVERY_YN"),
                            rs.getBoolean("PICKUP_YN"),
                            rs.getBoolean("DELETE_YN"),
                            rs.getString("IMAGE_PATH"),
                            rs.getString("IMAGE_PATH2"),
                            rs.getString("IMAGE_PATH3"),
                            rs.getDouble("SCORE"),
                            rs.getDouble("DISTANCE")),
                    getRestaurantParams);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public List<GetRestaurantRes> getRestaurantsByNameSearch(String searchRestaurantNameReq,int offset, int limit) {

        String getRestaurantQuery = "select RESTAURANT.*, IMAGE.IMAGE_PATH, IMAGE.IMAGE_PATH2, IMAGE.IMAGE_PATH3 from RESTAURANT  join IMAGE on RESTAURANT.RESTAURANT_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='RS' where BUSINESS_NAME like ? order by RESTAURANT.CREATION_DATE desc limit ?,?";

        System.out.println(getRestaurantQuery);
        System.out.println(searchRestaurantNameReq);
        String Param = "%" + searchRestaurantNameReq + "%";
        System.out.println(Param);

        return this.jdbcTemplate.query(getRestaurantQuery,
                (rs,rowNum) -> new GetRestaurantRes(
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getString("ADDRESS"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getString("REPRESENT_NAME"),
                        rs.getString("BUSINESS_NUMBER"),
                        rs.getString("OPERATING_TIME"),
                        rs.getString("INTRODUCTION_BOARD"),
                        rs.getString("ORIGIN_INFORMATION"),
                        rs.getInt("TIME_DELIVERY"),
                        rs.getInt("TIME_PICKUP"),
                        rs.getString("TIP_DELIVERY"),
                        rs.getString("MINIMUM_ORDER_PRICE"),
                        rs.getString("CATEGORY"),
                        rs.getBoolean("DELIVERY_YN"),
                        rs.getBoolean("FAST_DELIVERY_YN"),
                        rs.getBoolean("PICKUP_YN"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getString("IMAGE_PATH"),
                        rs.getString("IMAGE_PATH2"),
                        rs.getString("IMAGE_PATH3"),
                        rs.getDouble("SCORE"),
                        rs.getDouble("DISTANCE")),
                Param,offset,limit);
    }

    public List<GetRestaurantRes> getRestaurantsByCategorySearch(String searchRestaurantNameReq,int offset, int limit) {

        String getRestaurantQuery = "select RESTAURANT.*, IMAGE.IMAGE_PATH, IMAGE.IMAGE_PATH2, IMAGE.IMAGE_PATH3 from RESTAURANT  join IMAGE on RESTAURANT.RESTAURANT_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='RS' where CATEGORY like ? order by RESTAURANT.CREATION_DATE desc limit ?,?";

        System.out.println(getRestaurantQuery);
        System.out.println(searchRestaurantNameReq);
        String Param = "%" + searchRestaurantNameReq + "%";
        System.out.println(Param);


        return this.jdbcTemplate.query(getRestaurantQuery,
                (rs,rowNum) -> new GetRestaurantRes(
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getString("ADDRESS"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getString("REPRESENT_NAME"),
                        rs.getString("BUSINESS_NUMBER"),
                        rs.getString("OPERATING_TIME"),
                        rs.getString("INTRODUCTION_BOARD"),
                        rs.getString("ORIGIN_INFORMATION"),
                        rs.getInt("TIME_DELIVERY"),
                        rs.getInt("TIME_PICKUP"),
                        rs.getString("TIP_DELIVERY"),
                        rs.getString("MINIMUM_ORDER_PRICE"),
                        rs.getString("CATEGORY"),
                        rs.getBoolean("DELIVERY_YN"),
                        rs.getBoolean("FAST_DELIVERY_YN"),
                        rs.getBoolean("PICKUP_YN"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getString("IMAGE_PATH"),
                        rs.getString("IMAGE_PATH2"),
                        rs.getString("IMAGE_PATH3"),
                        rs.getDouble("SCORE"),
                        rs.getDouble("DISTANCE")),
                Param,offset,limit);
    }


    public List<GetRestaurantRes> getRestaurantsByAddress(String address,int offset, int limit) {

        String getRestaurantQuery = "select RESTAURANT.*, IMAGE.IMAGE_PATH, IMAGE.IMAGE_PATH2, IMAGE.IMAGE_PATH3 from RESTAURANT  join IMAGE on RESTAURANT.RESTAURANT_ID = IMAGE.TARGET_ID AND IMAGE.TARGET_CODE='RS' where ADDRESS like ? order by RESTAURANT.CREATION_DATE desc limit ?,?";

        System.out.println(getRestaurantQuery);
        System.out.println(address);
        String Param = "%" + address + "%";
        System.out.println(Param);

        return this.jdbcTemplate.query(getRestaurantQuery,
                (rs,rowNum) -> new GetRestaurantRes(
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getString("ADDRESS"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getString("REPRESENT_NAME"),
                        rs.getString("BUSINESS_NUMBER"),
                        rs.getString("OPERATING_TIME"),
                        rs.getString("INTRODUCTION_BOARD"),
                        rs.getString("ORIGIN_INFORMATION"),
                        rs.getInt("TIME_DELIVERY"),
                        rs.getInt("TIME_PICKUP"),
                        rs.getString("TIP_DELIVERY"),
                        rs.getString("MINIMUM_ORDER_PRICE"),
                        rs.getString("CATEGORY"),
                        rs.getBoolean("DELIVERY_YN"),
                        rs.getBoolean("FAST_DELIVERY_YN"),
                        rs.getBoolean("PICKUP_YN"),
                        rs.getBoolean("DELETE_YN"),
                        rs.getString("IMAGE_PATH"),
                        rs.getString("IMAGE_PATH2"),
                        rs.getString("IMAGE_PATH3"),
                        rs.getDouble("SCORE"),
                        rs.getDouble("DISTANCE")),
                Param,offset,limit);
    }



}
