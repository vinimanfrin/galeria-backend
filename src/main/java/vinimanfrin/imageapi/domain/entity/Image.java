package vinimanfrin.imageapi.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vinimanfrin.imageapi.domain.enums.ImageExtension;

import java.time.LocalDateTime;

@Entity
@Table(name = "imagens")
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    private String name;
    private Long size;
    private String tags;

    @CreatedDate
    private LocalDateTime uploadDate;

    @Lob
    private byte[] file;

    @Enumerated(EnumType.STRING)
    private ImageExtension extension;
}
