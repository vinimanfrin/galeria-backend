package vinimanfrin.imageapi.domain.service;

import vinimanfrin.imageapi.domain.entity.Image;
import vinimanfrin.imageapi.domain.enums.ImageExtension;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    Image save(Image image);
    Optional<Image> getById(String id);

    List<Image> search(ImageExtension extension, String query);
}
