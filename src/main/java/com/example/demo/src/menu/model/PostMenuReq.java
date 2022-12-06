package com.example.demo.src.menu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostMenuReq {

    private int menuId;

    private int restaurantId;

    private String gbCode;

    private String name;

    private int price;

    private String content;

    private int refId;

    private String menuImage;
    private int optionId;



}
