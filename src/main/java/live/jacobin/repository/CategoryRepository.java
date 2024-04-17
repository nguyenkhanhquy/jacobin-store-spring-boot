package live.jacobin.repository;

import live.jacobin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByName(String name);

    Category findByName(String name);

}
