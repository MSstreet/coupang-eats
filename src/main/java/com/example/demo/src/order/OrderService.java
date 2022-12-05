package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.src.order.model.PostOrderMenu;
import com.example.demo.src.order.model.PostOrderReq;
import com.example.demo.src.order.model.PostOrderRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class OrderService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OrderDao orderDao;
    private final OrderProvider orderProvider;
    private final JwtService jwtService;

    @Autowired
    public OrderService(OrderDao orderDao, OrderProvider orderProvider, JwtService jwtService) {
        this.orderDao = orderDao;
        this.orderProvider = orderProvider;
        this.jwtService = jwtService;

    }

    // 주문 등록
    public PostOrderRes createOrder(PostOrderReq postOrderReq) throws BaseException {
        try {
            int orderIdx = orderDao.createOrder(postOrderReq);
            for (PostOrderMenu menuList : postOrderReq.getOrderMenuList()){
                List<Integer> optionList = new ArrayList<>();
                int optionCount = menuList.getOrderMenuOptionList().size();
                for(int i = 0; i < 5; i++) {
                    if (i < optionCount) {
                        optionList.add(i, menuList.getOrderMenuOptionList().get(i));
                    }
                    else {
                        optionList.add(i, -1);
                    }
                }
                orderDao.createOrderMenu(orderIdx, menuList, optionList);
            }
            return new PostOrderRes(orderIdx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
