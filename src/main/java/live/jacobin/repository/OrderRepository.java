package live.jacobin.repository;

import live.jacobin.entity.Order;
import live.jacobin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUser(User user);

    List<Order> findByDateBetween(LocalDate firstDayOfMonth, LocalDate lastDayOfMonth);

    List<Order> findByDate(LocalDate specificDate);

}
