package vinimanfrin.imageapi.application.images;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vinimanfrin.imageapi.domain.entity.Image;
import java.io.IOException;
import java.net.URI;
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

        Image image = ImageMapper.mapToImage(file,name,tags);
        Image savedImage = imageService.save(image);
        URI imageUri = buildImageURL(savedImage);

        return ResponseEntity.created(imageUri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id){
        var possibleImage = imageService.getById(id);
        if (possibleImage.isEmpty()) return ResponseEntity.notFound().build();

        var image = possibleImage.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(image.getExtension().getMediaType());
        headers.setContentLength(image.getSize());
        headers.setContentDispositionFormData("inline; filename=\"" + image.getFileName() + "\"",image.getFileName());

        return new ResponseEntity<>(image.getFile(),headers, HttpStatus.OK);
    }

    private URI buildImageURL(Image image){
        String imagePath = "/"+image.getId();
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(imagePath)
                .build()
                .toUri();
    }
}
