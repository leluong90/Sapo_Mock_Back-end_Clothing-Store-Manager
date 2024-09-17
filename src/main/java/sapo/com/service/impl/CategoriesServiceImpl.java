package sapo.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sapo.com.model.dto.request.CategoriesRequest;
import sapo.com.model.entity.Brands;
import sapo.com.model.entity.Categories;
import sapo.com.model.entity.Products;
import sapo.com.repository.CategoriesRepository;
import sapo.com.repository.ProductsRepository;
import sapo.com.service.CategoriesService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    @Autowired
    private CategoriesRepository categoriesRepository ;
    @Autowired
    private ProductsRepository productsRepository ;
    @Override
    public Page<Categories> findAll(Pageable pageable) {
        return categoriesRepository.findAll(pageable);
    }

    @Override
    public Optional<Categories> findById(Integer id) throws Exception {
        Optional<Categories> category = findById(id);
        if (category.isPresent()){
            return category;
        }else {
            throw new Exception("Id not found");
        }

    }

    @Override
    public Categories findByCode(String code) throws Exception {
        Categories category = categoriesRepository.findByCode(code);
        if (category != null){
            return category;
        }else {
            throw new Exception("Code not found");
        }
    }

    @Override
    public Categories findByName(String name) throws Exception {
        Categories category = categoriesRepository.findByName(name);
        if (category != null){
            return category;
        }else {
            throw new Exception("Name not found");
        }
    }

    @Override
    public Categories create(CategoriesRequest categoriesRequest) throws Exception {
        if (categoriesRepository.findByCode(categoriesRequest.getCode()) == null){
            String code = generateCode();
            Categories category = Categories.builder()
                    .code(categoriesRequest.getCode())
                    .name(categoriesRequest.getName())
                    .description(categoriesRequest.getDescription())
                    .createdOn(LocalDateTime.now())
                    .build();
            return categoriesRepository.save(category);
        }else {
            throw new Exception("Exist Category");
        }


    }
    private String generateCode (){
        Random random = new Random();
        int randomNumber = random.nextInt(90000) + 10000;
        return "PGN"+ randomNumber;
    }

    @Override
    public Categories update(CategoriesRequest categoriesRequest , Integer id) throws Exception {
        Optional<Categories> category = categoriesRepository.findById(id);
        if (category.isPresent()){
            Categories categories = category.get();
            categories.setCode(categoriesRequest.getCode());
            categories.setName(categoriesRequest.getName());
            categories.setDescription(categoriesRequest.getDescription());
            categories.setUpdatedOn(LocalDateTime.now());
            return categoriesRepository.save(categories);

        }  else {
            throw new Exception("Id not found");
        }

    }

    @Override
    public void deleteById(Integer id) throws Exception {
        Optional<Categories> category = categoriesRepository.findById(id);
        if (category.isPresent()){
            categoriesRepository.deleteById(id);

        }else {
            throw new Exception("Id not found");
        }

    }
}
