package com.example.demo.src.menu_option.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostMenuOptionReq {

    private int menuOptionId;

    private int menuId;

    private String optionName;

}
