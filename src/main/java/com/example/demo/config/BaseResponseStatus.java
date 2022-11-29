package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    INVALID_PARAMETER(false, 2000, "파라미터 값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // [POST] /users
    POST_USERS_EMPTY_NAME(false, 2010, "이름을 입력해주세요."),
    POST_USERS_EMPTY_EMAIL(false, 2011, "아이디(이메일 주소)를 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false,2012,"아이디(이메일 주소)를 올바르게 입력해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2013,"이미 존재하는 아이디(이메일 주소)입니다."),
    POST_USERS_EMPTY_PASSWORD(false, 2014, "비밀번호를 입력해주세요."),
    POST_USERS_EMPTY_NICKNAME(false, 2015, "닉네임을 입력해주세요."),
    POST_USERS_EMPTY_PHONE(false, 2016, "휴대폰 번호를 입력해주세요."),
    POST_USERS_INVALID_PHONE(false, 2017, "휴대폰 번호를 올바르게 입력해주세요."),

    // [POST] /follows
    POST_FOLLOWS_INVALID_USER(false, 2016, "존재하지 않는 사용자입니다."),
    // [POST] /posts
    POST_POSTS_INVALID_USER(false, 2016, "존재하지 않는 사용자입니다."),

    //주소, 전화번호, 대표자명, 사업자번호, 운영시간, 배달팁, 최소주문가격, 레스토랑 사진, 치타, 배달, 포장
    POST_RESTAURANT_EMPTY_NUMBER(false, 2018, "가게 번호를 입력해주세요."),

    POST_RESTAURANT_EMPTY_REPRESENT(false, 2019, "대표자명 이름을 입력해주세요."),

    POST_RESTAURANT_EMPTY_BUSINESS_NUMBER(false, 2020, "사업자 번호를 입력해주세요."),

    POST_RESTAURANT_EMPTY_OPERATION(false, 2021, "운영시간을 입력해주세요."),

    POST_RESTAURANT_EMPTY_DELIVERY_TIP(false, 2021, "배달 금액을 입력해주세요."),

    POST_RESTAURANT_EMPTY_MINIMUM_ORDER_PRICE(false, 2022, "최소 주문금액을 입력해주세요."),

    POST_RESTAURANT_EMPTY_RESTAURANT_IMAGE(false, 2023, "가게 사진을 입력해주세요."),

    POST_RESTAURANT_EMPTY_RESTAURANT_NAME(false, 2027, "가게 이름를 입력해주세요."),
    POST_RESTAURANT_INVALID_NUMBER(false,2028,"전화번호 형식을 올바르게 입력해주세요."),

    POST_RESTAURANT_EXISTS_BUSINESS_NUMBER(false,2029,"이미 가입된 사업자 번호입니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    FAILED_TO_LOGIN(false,3010,"없는 아이디거나 비밀번호가 틀렸습니다."),
    FAILED_TO_LOGIN_DELETE(false,3011,"삭제된 계정입니다."),

    //[PATCH]
    MODIFY_FAIL(false,3015,"수정에 실패하였습니다."),
    DELETE_FAIL(false,3016,"삭제에 실패하였습니다."),
    UNFOLLOW_FAIL(false,3017,"팔로우 취소에 실패하였습니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),



    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    MODIFY_FAIL_RESTAURANT(false,4014,"유저정보 수정 실패");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
