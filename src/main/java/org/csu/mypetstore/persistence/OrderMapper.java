package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.domain.OrderStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderMapper {
    List<Order> getOrdersByUsername(String username);

    List<Order> getOrderList();

    List<OrderStatus> getOrderStatusList();


    Order getOrder(int orderId);

    void insertOrder(Order order);

    void insertOrderStatus(Order order);

    void updateOrder(Order order);

    void updateOrderStatus(OrderStatus orderStatus);

}
