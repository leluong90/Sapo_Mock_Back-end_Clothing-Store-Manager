package sapo.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sapo.com.model.dto.request.ProductsRequest;
import sapo.com.model.entity.Brands;
import sapo.com.model.entity.Categories;
import sapo.com.model.entity.Products;
import sapo.com.repository.BrandsRepository;
import sapo.com.repository.CategoriesRepository;
import sapo.com.repository.ImagePathsRepository;
import sapo.com.repository.ProductsRepository;
import sapo.com.service.BrandsService;
import sapo.com.service.CategoriesService;
import sapo.com.service.ProductsService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductsRepository productsRepository ;
    @Autowired
    private ImagePathsRepository imagePathsRepository ;
    @Autowired
    private CategoriesService categoriesService ;
    @Autowired
    private BrandsService brandsService ;
    @Override
    public Page<Products> findAll(Pageable pageable) {
        return productsRepository.findAll(pageable);
    }

    @Override
    public Optional<Products> findById(Integer id) throws Exception {
        Optional<Products> product = productsRepository.findById(id);
        if (product.isPresent()){
            return product;
        }else {
            throw new Exception("Id not found") ;
        }
    }

    @Override
    public Products findByName(String name) throws Exception {
        Products product = productsRepository.findByName(name);
        if (product !=null){
            return product;
        }else {
            throw new Exception("Name not found");
        }
    }

    @Override
    public Products create(ProductsRequest productsRequest) throws Exception {
        Categories  category = categoriesService.findByCode(productsRequest.getCategories().getCode());
        Brands brand = brandsService.findByCode(productsRequest.getBrands().getCode());
        Products product = productsRepository.findByName(productsRequest.getName());
        if (product == null){
            Products products = Products.builder()
                    .name(productsRequest.getName())
                    .categories(category)
                    .brands(brand)
                    .description(productsRequest.getDescription())
                    .totalQuantity(productsRequest.getTotalQuantity())
                    .status(productsRequest.getStatus())
                    .createdOn(LocalDateTime.now())
                    .build();
            return productsRepository.save(products);
        }else {
            throw new Exception("Exist name");
        }
    }

    @Override
    public Products update(ProductsRequest productsRequest , Integer id) throws Exception {
        Categories  category = categoriesService.findByCode(productsRequest.getCategories().getCode());
        Brands brand = brandsService.findByCode(productsRequest.getBrands().getCode());
        Optional<Products> product = productsRepository.findById(id);
        if (product.isPresent()){
            Products products = product.get();
            products.setName(productsRequest.getName());
            products.setCategories(category);
            products.setBrands(brand);
            products.setDescription(productsRequest.getDescription());
            products.setTotalQuantity(productsRequest.getTotalQuantity());
            products.setStatus(productsRequest.getStatus());
            products.setUpdatedOn(LocalDateTime.now());
            return productsRepository.save(products);
        } else {
            throw new Exception("Id not found");
        }
    }

    @Override
    public void delete(Integer id) throws Exception {
        Optional<Products> product = productsRepository.findById(id);
        if (product.isPresent()){
            productsRepository.deleteById(id);
        }else {
            throw new Exception("Id not found") ;
        }

    }
}
