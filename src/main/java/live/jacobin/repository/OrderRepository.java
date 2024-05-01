package live.jacobin.repository;

import live.jacobin.entity.Order;
import live.jacobin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findByOrderId(int orderId);

    Order findByUser(User user);
}
