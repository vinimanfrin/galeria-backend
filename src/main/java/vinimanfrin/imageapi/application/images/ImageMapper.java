package vinimanfrin.imageapi.application.images;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import vinimanfrin.imageapi.domain.entity.Image;
import vinimanfrin.imageapi.domain.enums.ImageExtension;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ImageMapper {

    public static Image mapToImage(MultipartFile file, String name, List<String> tags ) throws IOException {
        Image image = Image.builder()
                .name(name)
                .tags(String.join(",",tags))
                .uploadDate(LocalDateTime.now())
                .size(file.getSize())
                .extension(ImageExtension.valueoOf(MediaType.valueOf(file.getContentType())))
                .file(file.getBytes())
                .build();

        return image;
    }

    public static ImageDTO imageToDto(Image image, String url){
        return new ImageDTO(url,image.getName(),image.getExtension().name(), image.getSize(), image.getUploadDate().toLocalDate());
    }
}
