package live.jacobin.repository;

import live.jacobin.entity.LineItem;
import live.jacobin.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LineItemRepository extends CrudRepository<LineItem, Integer> {

    List<LineItem> findByProduct(Product product);

}
