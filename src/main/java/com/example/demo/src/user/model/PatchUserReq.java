package com.example.demo.src.user.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class PatchUserReq {
    private int userIdx;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String profileImagePath;
    private int addressIdx;
    private String addressDetail;
    private boolean deleteYn;
    private boolean marketingAgreeYn;
    private boolean informNoticeAgreeYn;
    private boolean orderNoticeAgreeYn;
}
