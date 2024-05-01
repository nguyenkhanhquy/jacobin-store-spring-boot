package live.jacobin.repository;

import live.jacobin.entity.Order;
import live.jacobin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUser(User user);
}
