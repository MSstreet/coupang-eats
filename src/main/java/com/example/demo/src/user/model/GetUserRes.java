package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private int userIdx;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private boolean deleteYn;
    private boolean marketingAgreeYn;
    private boolean informNoticeAgreeYn;
    private boolean orderNoticeAgreeYn;
}
