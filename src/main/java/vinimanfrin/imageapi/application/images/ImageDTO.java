package vinimanfrin.imageapi.application.images;

import com.fasterxml.jackson.annotation.JsonFormat;
import vinimanfrin.imageapi.domain.entity.Image;
import java.time.LocalDate;

public record ImageDTO(
        String url,
        String name,
        String extension,
        Long size,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate uploadDate
) {

    public ImageDTO(Image image,String url){
        this(url, image.getName(), image.getExtension().name(), image.getSize(), image.getUploadDate().toLocalDate());
    }

}
