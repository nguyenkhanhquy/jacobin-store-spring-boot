package live.jacobin.service;

import live.jacobin.entity.Order;
import live.jacobin.entity.User;
import live.jacobin.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public Order selectOrderById(int OrderId) {
        return orderRepository.findById(OrderId).orElse(null);
    }

    @Override
    public List<Order> selectOrderByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public List<Order> selectAllOrders() {
        return orderRepository.findAll();
    }
}
