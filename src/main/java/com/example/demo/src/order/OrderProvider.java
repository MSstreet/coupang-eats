package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.src.order.model.GetOrderMenu;
import com.example.demo.src.order.model.GetOrderRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class OrderProvider {
    private final OrderDao orderDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrderProvider(OrderDao orderDao, JwtService jwtService) {
        this.orderDao = orderDao;
        this.jwtService = jwtService;
    }

    // Orders 조회
    public List<GetOrderRes> getOrders(int offset, int limit) throws BaseException {
        try {
            List<GetOrderRes> getOrderRes = orderDao.getOrders(offset, limit);
            return getOrderRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // Order 조회
    public GetOrderRes getOrder(int orderIdx) throws BaseException {
        try {
            GetOrderRes getOrderRes = orderDao.getOrder(orderIdx);
            return getOrderRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // Orders Menu 조회
    public List<GetOrderMenu> getOrderMenus(int orderIdx) throws BaseException {
        try {
            List<GetOrderMenu> getOrderMenus = orderDao.getOrderMenus(orderIdx);
            return getOrderMenus;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 특정 user의 Orders 조회
    public List<GetOrderRes> getOrdersByUser(int userIdx, int offset, int limit) throws BaseException {
        try {
            List<GetOrderRes> getOrderRes = orderDao.getOrdersByUser(userIdx, offset, limit);
            return getOrderRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
