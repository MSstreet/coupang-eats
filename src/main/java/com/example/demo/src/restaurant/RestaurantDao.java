package com.example.demo.src.restaurant;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.restaurant.model.PostRestaurantReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@Repository
public class RestaurantDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createRestaurant(PostRestaurantReq postRestaurantReq){

        String createRestaurantQuery = "insert into restaurant (name,number,operation_time,tip_delivery,time_delivery,company_registration_number,categories,restaurant_image,min_delivery_price,possible_delivery,delete_flag,address_id) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";


        Object[] createRestaurantParams = new Object[]{postRestaurantReq.getName(), postRestaurantReq.getNumber(),postRestaurantReq.getOperationTime()
                ,postRestaurantReq.getTipDelivery(),postRestaurantReq.getTimeDelivery(),postRestaurantReq.getCompanyRegistrationNumber(),postRestaurantReq.getCategories()
                ,postRestaurantReq.getRestaurantImage(),postRestaurantReq.getMinDeliveryPrice()
                ,postRestaurantReq.getPossibleDelivery(),postRestaurantReq.getDeleteFlag()
                ,postRestaurantReq.getAddressId()};

        this.jdbcTemplate.update(createRestaurantQuery, createRestaurantParams);

        String lastInsertIdQuery = "select last_insert_id()";

        System.out.println(lastInsertIdQuery);

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int modifyRestaurant(PostRestaurantReq postRestaurantReq, int restaurantId){

        String modifyRestaurantQuery = "update restaurant set name = ? ,number = ?, operation_time = ?, tip_delivery = ?, time_delivery = ?, company_registration_number = ?, categories = ?,restaurant_image = ?," +
                " min_delivery_price = ?, possible_delivery = ?,  delete_flag = ?,  address_id = ? where restaurant_Id = ?";

        Object[] modifyRestaurantParams = new Object[]{postRestaurantReq.getName(), postRestaurantReq.getNumber(),postRestaurantReq.getOperationTime()
                ,postRestaurantReq.getTipDelivery(),postRestaurantReq.getTimeDelivery(),postRestaurantReq.getCompanyRegistrationNumber(),postRestaurantReq.getCategories()
                ,postRestaurantReq.getRestaurantImage(),postRestaurantReq.getMinDeliveryPrice()
                ,postRestaurantReq.getPossibleDelivery(),postRestaurantReq.getDeleteFlag()
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
}
