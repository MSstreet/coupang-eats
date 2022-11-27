package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
/**
 * Res.java: From Server To Client
 * 회원가입의 결과(Respone)를 보여주는 데이터의 형태
 */
public class PostUserRes {
    private int userIdx;
    private String jwt;
}
