package com.example.demo.src.user.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int userIdx;
    private String name;
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;
    private int profileImage;
    private int addressId;
    private String addressDetail;
    private boolean deleteYn;
    private boolean marketingAgreeYn;
    private boolean informNoticeAgreeYn;
    private boolean orderNoticeAgreeYn;
}
