package live.jacobin.service;

import live.jacobin.entity.Order;
import live.jacobin.entity.User;

import java.util.List;

public interface OrderService {

    void saveOrder(Order order);

    void deleteOrder(Order order);

    Order selectOrderById(int OrderId);

    List<Order> selectOrderByUser(User user);

    List<Order> selectAllOrders();
}
