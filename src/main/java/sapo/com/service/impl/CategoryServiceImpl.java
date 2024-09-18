package sapo.com.service.impl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sapo.com.exception.ResourceNotFoundException;
import sapo.com.model.dto.request.CategoryRequest;
import sapo.com.model.dto.response.CategoryResponse;
import sapo.com.model.entity.Category;
import sapo.com.repository.CategoryRepository;
import sapo.com.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    public Set<CategoryResponse> getListOfCategories(Long page, Long limit, String queryString){
        try{
            Set<Category> categories= categoryRepository.getListOfCategories(page,limit,queryString);
            Set<CategoryResponse> categoriesResponse = new HashSet<>();
            for(Category category: categories){
                categoriesResponse.add(category.transferToResponse());
            }
            return categoriesResponse;
        }catch(Exception e){
            log.error("Error:",e);
            return null;
        }
    }

    public CategoryResponse getCategoryById(Long id){
        try{
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
//            if(category.getStatus()==false){
//                throw new ResourceNotFoundException("Category no longer existed");
//            }
            return category.transferToResponse();
        }catch(Exception e){
            log.error("Error:",e);
            return null;
        }
    }

    @Transactional
    public CategoryResponse createNewCategory(CategoryRequest categoryRequest){
        try{
            Category category = new Category();
            category.setName(categoryRequest.getName());
            category.setCode(categoryRequest.getCode());
            category.setDescription(categoryRequest.getDescription());
            category.setStatus(true);
            category.setCreatedOn(LocalDateTime.now());
            category.setUpdatedOn(LocalDateTime.now());
            Category savedCategory = categoryRepository.save(category);
            return savedCategory.transferToResponse();
        }catch(Exception e){
            log.error("Error:",e);
            return null;
        }
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest){
        try{
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            category.setName(categoryRequest.getName());
            category.setCode(categoryRequest.getCode());
            category.setDescription(categoryRequest.getDescription());
            category.setUpdatedOn(LocalDateTime.now());
            Category savedCategory = categoryRepository.save(category);
            return savedCategory.transferToResponse();
        }catch(Exception e){
            log.error("Error:",e);
            return null;
        }
    }

    @Transactional
    public Boolean deleteCategoryById(Long id){
        try{
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            categoryRepository.deleteCategoryById(id);
            return true;
        }catch(Exception e){
            log.error("Error:",e);
            return false;
        }
    }
}
