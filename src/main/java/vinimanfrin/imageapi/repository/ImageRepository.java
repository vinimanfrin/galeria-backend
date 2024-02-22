package vinimanfrin.imageapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vinimanfrin.imageapi.domain.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image,String> {
}
