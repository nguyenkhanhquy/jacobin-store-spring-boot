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

    List<Order> selectOrdersInCurrentMonth();

    List<Order> selectOrdersInYear(int year);

    List<Order> selectOrdersInMonth(int year, int month);

    List<Order> selectOrdersInDay(int year, int month, int day);

}
