package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.order.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/orders")

public class OrderController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final OrderProvider orderProvider;
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final JwtService jwtService;

    public OrderController(OrderProvider orderProvider, OrderService orderService, JwtService jwtService) {
        this.orderProvider = orderProvider;
        this.orderService = orderService;
        this.jwtService = jwtService;
    }

    /**
     * 주문 등록 API
     * [POST] /orders
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostOrderRes> createOrder(@RequestBody PostOrderReq postOrderReq){
        if (postOrderReq.getUserIdx() == null){
            return new BaseResponse<>(POST_ORDERS_EMPTY_USER);
        }
        else if (postOrderReq.getUserIdx() < 1) {
            return new BaseResponse<>(POST_ORDERS_INVALID_USER);
        }
        else if (postOrderReq.getRestIdx() == null) {
            return new BaseResponse<>(POST_ORDERS_EMPTY_RESTAURANT);
        }
        else if (postOrderReq.getRestIdx() < 1) {
            return new BaseResponse<>(POST_ORDERS_INVALID_RESTAURANT);
        }
        else if (postOrderReq.getPrice() == null) {
            return new BaseResponse<>(POST_ORDERS_EMPTY_PRICE);
        }
        else if (postOrderReq.getOrderMenuList() == null || postOrderReq.getOrderMenuList().size() < 1) {
            return new BaseResponse<>(POST_ORDERS_EMPTY_MENU);
        }
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(postOrderReq.getUserIdx() != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostOrderRes postOrderRes = orderService.createOrder(postOrderReq);
            return new BaseResponse<>(postOrderRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 모든 주문들 조회 API
     * [GET] /orders
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetOrderAll>> getOrders() {
        try {
            List<GetOrderAll> orderAllList = new ArrayList<>();
            List<GetOrderRes> getOrdersRes = orderProvider.getOrders();
            for (GetOrderRes order : getOrdersRes) {
                List<GetOrderMenu> getOrderMenus = orderProvider.getOrderMenus(order.getOrderIdx());
                GetOrderAll getOrderAll = new GetOrderAll(order, getOrderMenus);
                orderAllList.add(getOrderAll);
            }
            return new BaseResponse<>(orderAllList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 인덱스로 주문 조회 API
     * [GET] /orders/:orderIdx
     */
    @ResponseBody
    @GetMapping("/{orderIdx}")
    public BaseResponse<GetOrderAll> getOrder(@PathVariable("orderIdx") int orderIdx) {
        try {
            GetOrderRes getOrderRes = orderProvider.getOrder(orderIdx);
            List<GetOrderMenu> getOrderMenus = orderProvider.getOrderMenus(orderIdx);
            GetOrderAll getOrderAll = new GetOrderAll(getOrderRes, getOrderMenus);

            return new BaseResponse<>(getOrderAll);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 사용자의 주문 조회 API
     * [GET] /orders/users/:userIdx
     */
    @ResponseBody
    @GetMapping("/users/{userIdx}")
    public BaseResponse<List<GetOrderAll>> getOrdersByUser(@PathVariable("userIdx") int userIdx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetOrderAll> orderAllList = new ArrayList<>();
            List<GetOrderRes> getOrdersRes = orderProvider.getOrdersByUser(userIdx);
            for (GetOrderRes order : getOrdersRes) {
                List<GetOrderMenu> getOrderMenus = orderProvider.getOrderMenus(order.getOrderIdx());
                GetOrderAll getOrderAll = new GetOrderAll(order, getOrderMenus);
                orderAllList.add(getOrderAll);
            }
            return new BaseResponse<>(orderAllList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
