package sapo.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sapo.com.model.entity.ImagePaths;

public interface ImagePathsRepository extends JpaRepository<ImagePaths , Integer> {
    @Query("select i from ImagePaths i where i.path= :path")
    ImagePaths findByPath (String path);
}
