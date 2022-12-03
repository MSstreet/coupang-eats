package com.example.demo.src.wish.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetWishRes {
    private int wishIdx;
    private int userIdx;
    private int restIdx;
    private boolean activeYn;
}
