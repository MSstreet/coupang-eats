package com.example.demo.src.address.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostAddressRes {
    // 주소 고유 번호
    private int addressId;

    private int userId;

    // 주소명
    private String addressName;

    private String detailAddress;

    private boolean addressPick;
}
