package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostOrderMenu {
    private Integer menuIdx;
    private List<Integer> orderMenuOptionList;
    private Integer count;
}
