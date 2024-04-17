package live.jacobin.service;

import live.jacobin.entity.Category;
import live.jacobin.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public boolean checkNameExists(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public Category selectCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    @Override
    public Category selectCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> selectAllCategory() {
        return categoryRepository.findAll();
    }

}
