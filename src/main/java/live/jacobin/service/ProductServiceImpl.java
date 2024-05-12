package live.jacobin.service;

import live.jacobin.entity.Product;
import live.jacobin.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public Product selectProductById(int productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public List<Product> selectProductByPartialName(String productNameSearch) {
        return productRepository.findByNameContaining(productNameSearch);
    }

    @Override
    public List<Product> selectAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> select20FirstProduct() {
        return productRepository.findFirst20ByOrderByProductId();
    }

    @Override
    public List<Product> selectNext10Product(int amount) {
        int page = (int) Math.ceil((double) amount / 10);
        Pageable pageable = PageRequest.of(page, 10);
        return productRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Product> select10FirstProductByCategoryId(int categoryId) {
        return productRepository.findFirst10ByCategoryCategoryId(categoryId);
    }

    @Override
    public List<Product> selectNext5ProductByCategoryId(int categoryId, int amount) {
        int page = (int) Math.ceil((double) amount / 5);
        Pageable pageable = PageRequest.of(page, 5);
        return productRepository.findByCategoryCategoryId(categoryId, pageable);
    }

}
