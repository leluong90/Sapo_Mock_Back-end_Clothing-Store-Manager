package sapo.com.service;

import sapo.com.model.dto.request.CategoryRequest;
import sapo.com.model.dto.response.CategoryResponse;

import java.util.Set;

public interface CategoryService {
    Set<CategoryResponse> getListOfCategories(Long page, Long limit, String queryString);
    CategoryResponse getCategoryById(Long id);
    CategoryResponse createNewCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest);
    Boolean deleteCategoryById(Long id);
}
