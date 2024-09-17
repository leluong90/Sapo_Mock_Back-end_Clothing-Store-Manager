package sapo.com.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sapo.com.model.dto.request.ImagePathsRequest;
import sapo.com.model.entity.ImagePaths;

import java.util.Optional;

public interface ImagePathsService {
    Page<ImagePaths> findAll (Pageable pageable);
    Optional<ImagePaths> findById(Integer id) throws Exception;
    ImagePaths create(ImagePathsRequest imagePathsRequest) throws Exception;
    ImagePaths update(ImagePathsRequest imagePathsRequest , Integer id) throws Exception;
    void deleteById (Integer id) throws Exception;
}
