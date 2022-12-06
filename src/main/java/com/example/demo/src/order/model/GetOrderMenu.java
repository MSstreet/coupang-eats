package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderMenu {
    private int orderMenuIdx;
    private int orderIdx;
    private String menuName;
    private int price;
    private int count;
    private String option1Name;
    private int option1Price;
    private String option2Name;
    private int option2Price;
    private String option3Name;
    private int option3Price;
    private String option4Name;
    private int option4Price;
    private String option5Name;
    private int option5Price;
}
