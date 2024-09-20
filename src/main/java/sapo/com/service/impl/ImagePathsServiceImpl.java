package sapo.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sapo.com.model.dto.request.ImagePathsRequest;
import sapo.com.model.entity.ImagePaths;
import sapo.com.repository.ImagePathsRepository;
import sapo.com.repository.ProductsRepository;
import sapo.com.service.ImagePathsService;

import java.util.Optional;

@Service
public class ImagePathsServiceImpl implements ImagePathsService {
    @Autowired
    private ImagePathsRepository imagePathsRepository ;
    @Autowired
    private ProductsRepository productsRepository ;
    @Override
    public Page<ImagePaths> findAll(Pageable pageable) {
        return imagePathsRepository.findAll(pageable);
    }

    @Override
    public Optional<ImagePaths> findById(Integer id) throws Exception {
        Optional<ImagePaths> imagePaths = imagePathsRepository.findById(id);
        if (imagePaths.isPresent()){
            return imagePaths;
        }else {
            throw new Exception("Id not found");
        }
    }

    @Override
    public ImagePaths create(ImagePathsRequest imagePathsRequest) throws Exception {
        if (imagePathsRepository.findByPath(imagePathsRequest.getPath()) == null){
            ImagePaths imagePath = ImagePaths.builder()
                    .path(imagePathsRequest.getPath())
                    .products(imagePathsRequest.getProducts())
                    .build();
            return imagePathsRepository.save(imagePath);
        } else {
            throw new Exception("Exist Path");
        }
    }

    @Override
    public ImagePaths update(ImagePathsRequest imagePathsRequest, Integer id) throws Exception {
        Optional<ImagePaths> imagePath = imagePathsRepository.findById(id);
        if (imagePath.isPresent()){
            ImagePaths imagePaths = imagePath.get();
            imagePaths.setPath(imagePathsRequest.getPath());
            imagePaths.setProducts(imagePathsRequest.getProducts());
            return imagePathsRepository.save(imagePaths);
        } else {
            throw new Exception("Id not found");
        }
    }

    @Override
    public void deleteById(Integer id) throws Exception {
        Optional<ImagePaths> imagePath = imagePathsRepository.findById(id);
        if (imagePath.isPresent()){
            imagePathsRepository.deleteById(id);
        } else {
            throw new Exception("Id not found");
        }

    }
}
