package live.jacobin.repository;

import live.jacobin.entity.Cart;
import live.jacobin.entity.LineItem;
import live.jacobin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findCartByUser(User user);

    @Query("SELECT c FROM Cart c WHERE c.items = :item")
    Cart findCartByLineItem(@Param("item") LineItem item);

}
