package live.jacobin.service;

import live.jacobin.entity.Product;

import java.util.List;

public interface ProductService {

    void saveProduct(Product product);

    void deleteProduct(Product product);

    Product selectProductById(int productId);

    List<Product> selectProductByPartialName(String productNameSearch);

    List<Product> selectAllProduct();

    List<Product> select20FirstProduct();

    List<Product> selectNext10Product(int amount);

    List<Product> select10FirstProductByCategoryId(int categoryId);

    List<Product> selectNext5ProductByCategoryId(int categoryId, int amount);

}
