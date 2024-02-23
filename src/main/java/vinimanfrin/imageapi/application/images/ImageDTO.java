package vinimanfrin.imageapi.application.images;

import vinimanfrin.imageapi.domain.entity.Image;

import java.time.LocalDate;

public record ImageDTO(
        String url,
        String name,
        String extension,
        Long size,
        LocalDate uploadDate
) {

    public ImageDTO(Image image,String url){
        this(url, image.getName(), image.getExtension().name(), image.getSize(), image.getUploadDate().toLocalDate());
    }

}
