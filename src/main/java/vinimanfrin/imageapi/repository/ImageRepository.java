package vinimanfrin.imageapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vinimanfrin.imageapi.domain.entity.Image;

public interface ImageRepository extends JpaRepository<Image,String> {
}
