package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderRes {
    private int orderIdx;
    private int userIdx;
    private int restIdx;
    private String restName;
    private String restImagePath;
    private int price;
    private String status;
    private int score;
    private String msgToOwner;
    private String msgToDeliveryMan;
    private boolean disposableYn;
    private boolean pickupYn;
    private Timestamp createDate;
}
