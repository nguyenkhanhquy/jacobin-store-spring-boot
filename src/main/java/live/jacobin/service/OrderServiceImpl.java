package live.jacobin.service;

import live.jacobin.entity.Order;
import live.jacobin.entity.User;
import live.jacobin.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
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

    @Override
    public List<Order> selectOrdersInCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfMonth = currentDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfMonth = currentDate.with(TemporalAdjusters.lastDayOfMonth());
        return orderRepository.findByDateBetween(firstDayOfMonth, lastDayOfMonth);
    }

    @Override
    public List<Order> selectOrdersInYear(int year) {
        LocalDate firstDayOfYear = LocalDate.of(year, Month.JANUARY, 1); // Ngày đầu tiên của năm nhập vào
        LocalDate lastDayOfYear = LocalDate.of(year, Month.DECEMBER, 31); // Ngày cuối cùng của năm nhập vào

        return orderRepository.findByDateBetween(firstDayOfYear, lastDayOfYear);
    }

    @Override
    public List<Order> selectOrdersInMonth(int year, int month) {
        // Ngày đầu tiên của tháng và năm nhập vào
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);

        // Ngày cuối cùng của tháng và năm nhập vào
        LocalDate lastDayOfMonth = firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());

        // Lọc danh sách các đơn hàng trong khoảng thời gian từ ngày đầu tiên đến ngày cuối cùng của tháng và năm
        return orderRepository.findByDateBetween(firstDayOfMonth, lastDayOfMonth);
    }

    @Override
    public List<Order> selectOrdersInDay(int year, int month, int day) {
        // Ngày cụ thể cần tìm
        LocalDate specificDate = LocalDate.of(year, month, day);

        // Lọc danh sách các đơn hàng trong ngày cụ thể
        return orderRepository.findByDate(specificDate);
    }

}
