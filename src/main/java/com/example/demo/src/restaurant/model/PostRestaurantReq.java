package com.example.demo.src.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class PostRestaurantReq {
    // 가게 고유 번호
    private int restaurantId;

    // 가게상호
    private String name;

    // 가게 전화번호
    private String number;

    // 운영시간
    private String operationTime;

    // 가게소게글
    private String introductionBoard;

    // 배달비
    private String tipDelivery;

    // 배달시간
    private String timeDelivery;

    // 사업자번호
    private String companyRegistrationNumber;

    // 카테고리
    private int categories;

    // 가게 사진
    private String restaurantImage;

    // 최소 주문 비용
    private String minDeliveryPrice;

    // 휴무일
    private String closedDay;

    // 배달 지역
    //private String possibleDelivery;

    // 삭제 여부
    private Boolean deleteFlag;

    // 편의시설
    private String facilities;

    // 배달 가능 여부
    private Boolean deliveryAvlb;

    // 치타배달 가능 여부
    private Boolean fastDeliveryAvlb;

    // 포장 가능 여부
    private Boolean pickupAvlb;

    // 주소 고유 번호
    private String address;

    // 상세 주소
    //private String detailAddress;

    private String representName;

    private String originInfo;
}
