package com.example.demo.src.wish.model;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostWishReq {
    private Integer userIdx;
    private Integer restIdx;
}