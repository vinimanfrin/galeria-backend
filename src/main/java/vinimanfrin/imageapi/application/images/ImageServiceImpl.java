package vinimanfrin.imageapi.application.images;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vinimanfrin.imageapi.domain.entity.Image;
import vinimanfrin.imageapi.domain.service.ImageService;
import vinimanfrin.imageapi.repository.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository repository;

    @Override
    @Transactional
    public Image save(Image image) {
        return repository.save(image);
    }
}
