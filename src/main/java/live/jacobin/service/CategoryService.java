package live.jacobin.service;

import live.jacobin.entity.Category;

import java.util.List;

public interface CategoryService {

    void saveCategory(Category category);

    void deleteCategory(Category category);

    boolean checkNameExists(String name);

    Category selectCategoryById(int categoryId);

    Category selectCategoryByName(String name);

    List<Category> selectAllCategory();

}
