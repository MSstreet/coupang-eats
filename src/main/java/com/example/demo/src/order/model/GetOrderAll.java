package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderAll {
    private GetOrderRes getOrderRes;
    private List<GetOrderMenu> orderMenuList;
}
