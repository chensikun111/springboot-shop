package org.example.shopvue.utils;

import org.example.shopvue.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageSelect {
    @Autowired
    private OrderMapper orderMapper;

//    public List<Order> selectPage(Order order, Pageable pageable) {
//
//    }
}
