package com.example.demo.src.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetRestaurantRes {

    // 가게 고유 번호
    private int restaurantId;

    // 가게상호
    private String name;

    // 주소 고유 번호
    private int addressId;

    // 상세 주소
    private String detailAddress;

    // 가게 전화번호
    private String number;



    private String ownerName;

    // 사업자번호
    private String companyRegistrationNumber;

    // 운영시간
    private String operationTime;

    // 가게소게글
    private String introductionBoard;

    private String originInfo;

    // 배달시간
    private String timeDelivery;

    private String timePick;
    // 배달비
    private String tipDelivery;


    // 최소 주문 비용
    private String minDeliveryPrice;

    // 카테고리
    private int categories;

    // 삭제 여부
    private Boolean deleteFlag;

    // 휴무일
    //private String closedDay;

    // 배달 지역
    //private String possibleDelivery;

    // 치타배달 가능 여부
    private Boolean fastDeliveryAvlb;

    // 편의시설
    //private String facilities;
    // 포장 가능 여부
    private Boolean pickupAvlb;

    // 배달 가능 여부
    private Boolean deliveryAvlb;

    // 가게 사진
    private String restaurantImage;


}
