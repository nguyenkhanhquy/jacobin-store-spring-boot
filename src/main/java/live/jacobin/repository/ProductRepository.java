package live.jacobin.repository;

import live.jacobin.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameContaining(String productNameSearch);

    List<Product> findFirst20ByOrderByProductId();

    List<Product> findFirst10ByCategoryCategoryId(int categoryId);

    List<Product> findByCategoryCategoryId(int categoryId, Pageable pageable);

}
