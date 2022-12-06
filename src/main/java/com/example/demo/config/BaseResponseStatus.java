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

    // [POST], [PATCH] /users
    POST_USERS_EMPTY_NAME(false, 2010, "이름을 입력해주세요."),
    PATCH_USERS_EMPTY_NAME(false, 2010, "이름을 입력해주세요."),

    POST_USERS_EMPTY_EMAIL(false, 2011, "아이디(이메일 주소)를 입력해주세요."),
    PATCH_USERS_EMPTY_EMAIL(false, 2011, "아이디(이메일 주소)를 입력해주세요."),

    POST_USERS_INVALID_EMAIL(false,2012,"아이디(이메일 주소)를 올바르게 입력해주세요."),
    PATCH_USERS_INVALID_EMAIL(false,2012,"아이디(이메일 주소)를 올바르게 입력해주세요."),

    POST_USERS_EXISTS_EMAIL(false,2013,"이미 존재하는 아이디(이메일 주소)입니다."),
    PATCH_USERS_EXISTS_EMAIL(false,2013,"이미 존재하는 아이디(이메일 주소)입니다."),

    POST_USERS_EMPTY_PASSWORD(false, 2014, "비밀번호를 입력해주세요."),
    PATCH_USERS_EMPTY_PASSWORD(false, 2014, "비밀번호를 입력해주세요."),
    POST_USERS_EMPTY_NICKNAME(false, 2015, "닉네임을 입력해주세요."),
    POST_USERS_EMPTY_PHONE(false, 2016, "휴대폰 번호를 입력해주세요."),
    PATCH_USERS_EMPTY_PHONE(false, 2016, "휴대폰 번호를 입력해주세요."),

    POST_USERS_INVALID_PHONE(false, 2017, "휴대폰 번호를 올바르게 입력해주세요."),
    PATCH_USERS_INVALID_PHONE(false, 2017, "휴대폰 번호를 올바르게 입력해주세요."),



    //[POST] / RESTAURANT
    //주소, 전화번호, 대표자명, 사업자번호, 운영시간, 배달팁, 최소주문가격, 레스토랑 사진, 치타, 배달, 포장
    POST_RESTAURANT_EMPTY_NUMBER(false, 2020, "가게 번호를 입력해주세요."),

    POST_RESTAURANT_EMPTY_REPRESENT(false, 2021, "대표자명 이름을 입력해주세요."),

    POST_RESTAURANT_EMPTY_BUSINESS_NUMBER(false, 2022, "사업자 번호를 입력해주세요."),

    POST_RESTAURANT_EMPTY_OPERATION(false, 2023, "운영시간을 입력해주세요."),

    POST_RESTAURANT_EMPTY_DELIVERY_TIP(false, 2024, "배달 금액을 입력해주세요."),

    POST_RESTAURANT_EMPTY_MINIMUM_ORDER_PRICE(false, 2025, "최소 주문금액을 입력해주세요."),

    POST_RESTAURANT_EMPTY_RESTAURANT_IMAGE(false, 2026, "가게 사진을 입력해주세요."),

    POST_RESTAURANT_EMPTY_RESTAURANT_NAME(false, 2027, "가게 이름를 입력해주세요."),
    POST_RESTAURANT_INVALID_NUMBER(false,2028,"전화번호 형식을 올바르게 입력해주세요."),

    POST_RESTAURANT_EXISTS_BUSINESS_NUMBER(false,2029,"이미 가입된 사업자 번호입니다."),

    POST_RESTAURANT_EMPTY_ADDRESS(false, 2041, "가게 주소를 입력해주세요."),




    // [POST] / MENU

    POST_MENU_EMPTY_GB_CODE(false, 2024, "값을 입력해주세요."),

    POST_MENU_EMPTY_NAME(false, 2025, "메뉴명을 입력해주세요."),

    POST_MENU_EMPTY_PRICE(false, 2026, "가격을 입력해주세요."),




    // [POST] /reviews
    POST_REVIEWS_EMPTY_ORDER(false, 2030, "주문 번호를 입력해주세요."),
    POST_REVIEWS_INVALID_ORDER(false, 2031, "유효하지 않은 주문 번호입니다."),
    POST_REVIEWS_EXISTS_ORDER(false, 2032, "해당 주문에는 이미 리뷰가 존재합니다."),
    POST_REVIEWS_EMPTY_SCORE(false, 2033, "별점 값을 입력해주세요."),
    PATCH_REVIEWS_EMPTY_SCORE(false, 2033, "별점 값을 입력해주세요."),

    POST_REVIEWS_INVALID_SCORE(false, 2034, "유효하지 않은 별점 값입니다."),
    PATCH_REVIEWS_INVALID_SCORE(false, 2034, "유효하지 않은 별점 값입니다."),


    // [POST] /orders
    POST_ORDERS_EMPTY_USER(false, 2035, "주문한 사용자를 입력해주세요."),
    POST_ORDERS_INVALID_USER(false, 2036, "유효하지 않은 사용자 번호입니다."),
    POST_ORDERS_EMPTY_RESTAURANT(false, 2037, "주문할 가게를 입력해주세요."),
    POST_ORDERS_INVALID_RESTAURANT(false, 2038, "유효하지 않은 가게 번호입니다."),
    POST_ORDERS_EMPTY_PRICE(false, 2039, "가격을 입력해주세요."),
    POST_ORDERS_EMPTY_MENU(false, 2040, "메뉴를 선택해주세요."),

    // [POST] /wishlists
    POST_WISHLISTS_EMPTY_USER(false, 2041, "찜한 사용자를 입력해주세요."),
    POST_WISHLISTS_INVALID_USER(false, 2042, "유효하지 않은 사용자 번호입니다."),
    POST_WISHLISTS_EMPTY_RESTAURANT(false, 2043, "찜할 가게를 입력해주세요."),
    POST_WISHLISTS_INVALID_RESTAURANT(false, 2044, "유효하지 않은 가게 번호입니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    FAILED_TO_LOGIN(false,3010,"없는 아이디거나 비밀번호가 틀렸습니다."),
    FAILED_TO_LOGIN_DELETE(false,3011,"삭제된 계정입니다."),

    //[PATCH]
    FAILED_TO_MODIFY(false,3015,"수정에 실패하였습니다."),
    FAILED_TO_DELETE(false,3016,"삭제에 실패하였습니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    DATABASE_ERROR_USER_ID(false, 4002, "사용자 ID 조회에 실패하였습니다."),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    MODIFY_FAIL_RESTAURANT(false,4014,"가게 정보 수정 실패");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
