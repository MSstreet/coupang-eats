package com.example.demo.src.order;


import com.example.demo.src.order.model.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository

public class OrderDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 주문 등록
    public int createOrder(PostOrderReq postOrderReq) {
        String createOrderQuery = "insert into ORDERS (USER_ID, RESTAURANT_ID, PRICE, STATUS, MSG_TO_OWNER, MSG_TO_DELIVERYMAN, DISPOSABLE_YN, PICKUP_YN) values (?,?,?,?,?,?,?,?)";
        Object[] createOrderParams = new Object[]{postOrderReq.getUserIdx(), postOrderReq.getRestIdx(), postOrderReq.getPrice(),
                postOrderReq.getStatus() == null ? "" : postOrderReq.getStatus(),
                postOrderReq.getMsgToOwner() == null ? "" : postOrderReq.getMsgToOwner(),
                postOrderReq.getMsgToDeliveryMan() == null ? "" : postOrderReq.getMsgToDeliveryMan(),
                postOrderReq.isDisposableYn(), postOrderReq.isPickupYn()};
        this.jdbcTemplate.update(createOrderQuery, createOrderParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    // 주문 메뉴 등록
    public int createOrderMenu(int orderIdx, PostOrderMenu postOrderMenu, List<Integer> optionList) {
        String createOrderQuery = "insert into ORDER_MENU (ORDER_ID, MENU_ID, OPTION_1, OPTION_2, OPTION_3, OPTION_4, OPTION_5, COUNT) values (?,?,?,?,?,?,?,?)";
        Object[] createOrderParams = new Object[]{orderIdx, postOrderMenu.getMenuIdx(),
                optionList.get(0) == -1 ? null :  optionList.get(0),
                optionList.get(1) == -1 ? null :  optionList.get(1),
                optionList.get(2) == -1 ? null :  optionList.get(2),
                optionList.get(3) == -1 ? null :  optionList.get(3),
                optionList.get(4) == -1 ? null :  optionList.get(4),
                postOrderMenu.getCount()};
        this.jdbcTemplate.update(createOrderQuery, createOrderParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    // Order 테이블에 존재하는 전체 주문 메뉴 정보 조회
    public List<GetOrderMenu> getOrderMenus(int orderIdx) {
        String getOrdersQuery = "select OM.*, M1.NAME as MENU_NAME, M1.PRICE as MENU_PRICE, " +
                "M2.NAME as OPTION1_NAME, M2.PRICE as OPTION1_PRICE, " +
                "M3.NAME as OPTION2_NAME, M3.PRICE as OPTION2_PRICE, " +
                "M4.NAME as OPTION3_NAME, M4.PRICE as OPTION3_PRICE, " +
                "M5.NAME as OPTION4_NAME, M5.PRICE as OPTION4_PRICE, " +
                "M6.NAME as OPTION5_NAME, M6.PRICE as OPTION5_PRICE " +
                "from ORDER_MENU OM " +
                "join MENU M1 on OM.MENU_ID = M1.MENU_ID and M1.GB_CODE = 'MN' " +
                "left join MENU M2 on OM.OPTION_1 = M2.MENU_ID and M2.GB_CODE = 'OP' " +
                "left join MENU M3 on OM.OPTION_2 = M2.MENU_ID and M2.GB_CODE = 'OP' " +
                "left join MENU M4 on OM.OPTION_3 = M2.MENU_ID and M2.GB_CODE = 'OP' " +
                "left join MENU M5 on OM.OPTION_4 = M2.MENU_ID and M2.GB_CODE = 'OP' " +
                "left join MENU M6 on OM.OPTION_5 = M2.MENU_ID and M2.GB_CODE = 'OP' " +
                "where OM.ORDER_ID = ?";
        return this.jdbcTemplate.query(getOrdersQuery,
                (rs, rowNum) -> new GetOrderMenu(
                        rs.getInt("ORDER_MENU_ID"),
                        rs.getInt("ORDER_ID"),
                        rs.getString("MENU_NAME"),
                        rs.getInt("MENU_PRICE"),
                        rs.getInt("COUNT"),
                        rs.getString("OPTION1_NAME"),
                        rs.getInt("OPTION1_PRICE"),
                        rs.getString("OPTION2_NAME"),
                        rs.getInt("OPTION2_PRICE"),
                        rs.getString("OPTION3_NAME"),
                        rs.getInt("OPTION3_PRICE"),
                        rs.getString("OPTION4_NAME"),
                        rs.getInt("OPTION4_PRICE"),
                        rs.getString("OPTION5_NAME"),
                        rs.getInt("OPTION5_PRICE")
                ), orderIdx
        );
    }

    // Order 테이블에 존재하는 전체 주문 정보 조회
    public List<GetOrderRes> getOrders() {
        String getOrdersQuery = "select ORDERS.*, R.BUSINESS_NAME, I.IMAGE_PATH, R2.SCORE from ORDERS " +
                "join RESTAURANT R on ORDERS.RESTAURANT_ID = R.RESTAURANT_ID " +
                "left join IMAGE I on R.RESTAURANT_ID = I.TARGET_ID and I.TARGET_CODE = 'RS' " +
                "left join REVIEW R2 on ORDERS.ORDER_ID = R2.ORDER_ID and R2.DELETE_YN = false";
        return this.jdbcTemplate.query(getOrdersQuery,
                (rs, rowNum) -> new GetOrderRes(
                        rs.getInt("ORDER_ID"),
                        rs.getInt("USER_ID"),
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getString("IMAGE_PATH"),
                        rs.getInt("PRICE"),
                        rs.getString("STATUS"),
                        rs.getInt("SCORE"),
                        rs.getString("MSG_TO_OWNER"),
                        rs.getString("MSG_TO_DELIVERYMAN"),
                        rs.getBoolean("DISPOSABLE_YN"),
                        rs.getBoolean("PICKUP_YN"),
                        rs.getTimestamp("CREATION_DATE")
                )
        );
    }

    // (특정 유저의) Order 테이블에 존재하는 전체 주문 정보 조회
    public List<GetOrderRes> getOrdersByUser(int userIdx) {
        String getOrdersQuery = "select ORDERS.*, R.BUSINESS_NAME, I.IMAGE_PATH, R2.SCORE from ORDERS " +
                "join RESTAURANT R on ORDERS.RESTAURANT_ID = R.RESTAURANT_ID " +
                "left join IMAGE I on R.RESTAURANT_ID = I.TARGET_ID and I.TARGET_CODE = 'RS' " +
                "left join REVIEW R2 on ORDERS.ORDER_ID = R2.ORDER_ID and R2.DELETE_YN = false " +
                "where ORDERS.USER_ID = ? order by ORDERS.CREATION_DATE desc";
        return this.jdbcTemplate.query(getOrdersQuery,
                (rs, rowNum) -> new GetOrderRes(
                        rs.getInt("ORDER_ID"),
                        rs.getInt("USER_ID"),
                        rs.getInt("RESTAURANT_ID"),
                        rs.getString("BUSINESS_NAME"),
                        rs.getString("IMAGE_PATH"),
                        rs.getInt("PRICE"),
                        rs.getString("STATUS"),
                        rs.getInt("SCORE"),
                        rs.getString("MSG_TO_OWNER"),
                        rs.getString("MSG_TO_DELIVERYMAN"),
                        rs.getBoolean("DISPOSABLE_YN"),
                        rs.getBoolean("PICKUP_YN"),
                        rs.getTimestamp("CREATION_DATE")
                ), userIdx
        );
    }
}
