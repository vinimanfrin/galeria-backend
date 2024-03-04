package vinimanfrin.imageapi.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vinimanfrin.imageapi.domain.entity.Image;
import vinimanfrin.imageapi.domain.enums.ImageExtension;
import vinimanfrin.imageapi.repository.specs.ImageSpecs;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,String>, JpaSpecificationExecutor<Image> {

    /**
     * Retorna uma lista de imagens que correspondem à extensão fornecida e/ou contêm o nome ou tags semelhantes à consulta especificada.
     *
     * A pesquisa pode ser filtrada por extensão e/ou palavra-chave. Se uma extensão for especificada, apenas imagens com essa extensão serão retornadas.
     * Se uma palavra-chave for fornecida, a pesquisa será realizada nos nomes das imagens e/ou nas tags associadas, retornando imagens cujos nomes ou tags
     * contenham a palavra-chave, insensíveis a maiúsculas e minúsculas..
     */
    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query){
        Specification<Image> conjuction = (root,q,criteriaBuilder) -> criteriaBuilder.conjunction();
        Specification<Image> spec = Specification.where(conjuction);

        if (extension != null){
            spec = spec.and(ImageSpecs.extensionEqual(extension));
        }

        if (StringUtils.hasText(query)){
            Specification<Image> nameOrTagsLike = Specification.anyOf(ImageSpecs.nameLike(query),ImageSpecs.tagsLike(query));
            spec = spec.and(nameOrTagsLike);
        }

        // Retorna a lista de imagens correspondentes aos critérios de pesquisa fornecidos
        return findAll(spec);
    }
}
