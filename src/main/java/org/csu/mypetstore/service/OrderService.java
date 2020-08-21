package org.csu.mypetstore.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.LineItemMapper;
import org.csu.mypetstore.persistence.OrderMapper;
import org.csu.mypetstore.persistence.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private LineItemMapper lineItemMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SequenceMapper sequenceMapper;

    @Transactional
    public void insertOrder(Order order) {
//        设置order 的 orderId
        order.setOrderId(getNextId("ordernum"));
//        更新存货数量
        for (int i = 0; i < order.getLineItems().size(); i ++) {
            LineItem lineItem = order.getLineItems().get(i);
            String itemId = lineItem.getItemId();
            Integer increment = lineItem.getQuantity();
            Map<String, Object> param = new HashMap<String, Object>(2);
            param.put("itemId", itemId);
            param.put("increment", increment);
            itemMapper.updateInventoryQuantity(param);
        }
//        向数据库添加 order
        orderMapper.insertOrder(order);
        orderMapper.insertOrderStatus(order);
//        重置 order 中 lineItem 的 orderId
        for(int i = 0; i < order.getLineItems().size(); i ++) {
            LineItem lineItem = order.getLineItems().get(i);
            lineItem.setOrderId(order.getOrderId());
            lineItemMapper.insertLineItem(lineItem);
        }
    }
    public void updateOrder(Order order){orderMapper.updateOrder(order);}

    public void updateOrderStatus(OrderStatus orderStatus){orderMapper.updateOrderStatus(orderStatus);}

    @Transactional
    public Order getOrder(int orderId) {
        Order order = orderMapper.getOrder(orderId);
        order.setLineItems(lineItemMapper.getLineItemsByOrderId(orderId));

        for(int i = 0; i < order.getLineItems().size(); i ++) {
            LineItem lineItem = order.getLineItems().get(i);
            Item item = itemMapper.getItem(lineItem.getItemId());
            item.setQuantity(itemMapper.getInventoryQuantity(lineItem.getItemId()));
            lineItem.setItem(item);
        }
        return order;
    }
    //  根据 username 获取 该用户的所有 order
    public List<Order> getOrdersByUsername(String username) {
        return orderMapper.getOrdersByUsername(username);
    }

    //    获取 lineItem 或 cartItem 序号数
    public int getNextId(String name) {
        Sequence sequence = new Sequence(name, -1);
        sequence = sequenceMapper.getSequence(sequence);
        if (sequence == null) {
            throw new RuntimeException("Error: A null sequence was returned from the database (could not get next " + name
                    + " sequence).");
        }
        Sequence parameterObject = new Sequence(name, sequence.getNextId() + 1);
        sequenceMapper.updateSequence(parameterObject);
        return sequence.getNextId();
    }
    public PageInfo<Order> getOrderList(int page, int limit){
        PageHelper.startPage(page,limit);
        List<Order> orderList = orderMapper.getOrderList();
        System.out.println(orderList.size()+"******************");
        PageInfo<Order> pageInfo = new PageInfo<Order>(orderList);
        return pageInfo;
    }


    public PageInfo<Order> searchOrderByUsername(int page, int limit, String username) {
        PageHelper.startPage(page,limit);
        List<Order> orderList = orderMapper.getOrdersByUsername(username);
        //System.out.println(orderList.size()+"******************");
        PageInfo<Order> pageInfo = new PageInfo<Order>(orderList);
        return pageInfo;
    }

    public PageInfo<OrderStatus> getOrderStatus(int page, int limit) {
        PageHelper.startPage(page,limit);
        List<OrderStatus> orderList = orderMapper.getOrderStatusList();
        //System.out.println(orderList.size()+"******************");
        PageInfo<OrderStatus> pageInfo = new PageInfo<OrderStatus>(orderList);
        return pageInfo;
    }
}
