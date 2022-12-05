package com.example.demo.src.order.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostOrderReq {
    private Integer userIdx;
    private Integer restIdx;
    private Integer price;
    private String status;
    private String msgToOwner;
    private String msgToDeliveryMan;
    private boolean disposableYn;
    private boolean pickupYn;
    private List<PostOrderMenu> orderMenuList;
}