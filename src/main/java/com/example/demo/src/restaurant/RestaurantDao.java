package com.example.demo.src.restaurant;

import com.example.demo.src.restaurant.model.PostRestaurantReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class RestaurantDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createRestaurant(PostRestaurantReq postRestaurantReq){

        String createUserQuery = "insert into restaurant (name,number,operation_time,tip_delivery,time_delivery,company_registration_number,categories,restaurant_image,min_delivery_price,possible_delivery,delete_flag,address_id) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";


        Object[] createRestaurantParams = new Object[]{postRestaurantReq.getName(), postRestaurantReq.getNumber(),postRestaurantReq.getOperationTime()
                ,postRestaurantReq.getTipDelivery(),postRestaurantReq.getTimeDelivery(),postRestaurantReq.getCompanyRegistrationNumber(),postRestaurantReq.getCategories()
                ,postRestaurantReq.getRestaurantImage(),postRestaurantReq.getMinDeliveryPrice()
                ,postRestaurantReq.getPossibleDelivery(),postRestaurantReq.getDeleteFlag()
                ,postRestaurantReq.getAddressId()};

        this.jdbcTemplate.update(createUserQuery, createRestaurantParams);

        String lastInsertIdQuery = "select last_insert_id()";

        System.out.println(lastInsertIdQuery);

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

}
