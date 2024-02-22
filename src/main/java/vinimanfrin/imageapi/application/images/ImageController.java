package vinimanfrin.imageapi.application.images;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vinimanfrin.imageapi.domain.entity.Image;
import vinimanfrin.imageapi.domain.enums.ImageExtension;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/image")
@Slf4j
public class ImageController {

    @Autowired
    private ImageServiceImpl imageService;

    @PostMapping
    public ResponseEntity save (
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("tags") List<String> tags
            ) throws IOException {

        log.info("Imagem recebida: name: {}, size: {}", file.getOriginalFilename(), file.getSize());

        Image image = Image.builder()
                .name(name)
                .tags(String.join(",",tags))
                .size(file.getSize())
                .extension(ImageExtension.valueoOf(MediaType.valueOf(file.getContentType())))
                .file(file.getBytes())
                .build();

        imageService.save(image);

        return ResponseEntity.ok().build();
    }
}
