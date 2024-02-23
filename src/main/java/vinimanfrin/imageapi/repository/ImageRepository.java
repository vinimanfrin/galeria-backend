package vinimanfrin.imageapi.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vinimanfrin.imageapi.domain.entity.Image;
import vinimanfrin.imageapi.domain.enums.ImageExtension;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,String>, JpaSpecificationExecutor<Image> {

    /**
     * Retorna uma lista de imagens que correspondem à extensão fornecida e/ou contêm o nome ou tags semelhantes à consulta especificada.
     *
     * A pesquisa pode ser filtrada por extensão e/ou palavra-chave. Se uma extensão for especificada, apenas imagens com essa extensão serão retornadas.
     * Se uma palavra-chave for fornecida, a pesquisa será realizada nos nomes das imagens e/ou nas tags associadas, retornando imagens cujos nomes ou tags
     * contenham a palavra-chave, insensíveis a maiúsculas e minúsculas.
     *
     * @param extension A extensão da imagem a ser pesquisada. Pode ser nulo para não aplicar nenhum filtro de extensão.
     * @param query A palavra-chave para pesquisar nos nomes das imagens e/ou nas tags associadas.
     * @return Uma lista de imagens correspondentes aos critérios de pesquisa fornecidos.
     */
    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query){

        // Inicializa uma especificação de conjunção para construir as especificações de pesquisa
        Specification<Image> conjuction = (root,q,criteriaBuilder) -> criteriaBuilder.conjunction();
        Specification<Image> spec = Specification.where(conjuction);

        // Adiciona o critério de pesquisa por extensão, se fornecido
        if (extension != null){
            Specification<Image> extensionEqual = (root,q,cb) -> cb.equal(root.get("extension"), extension);
            spec = spec.and(extensionEqual);
        }

        // Adiciona o critério de pesquisa por palavra-chave, se fornecida
        if (StringUtils.hasText(query)){

            // Cria especificações para pesquisar por nome e tags semelhantes à consulta fornecida
            Specification<Image> nameLike = (root,q,cb) -> cb.like( cb.upper(root.get("name")) , "%" + query.toUpperCase() + "%");
            Specification<Image> tagsLike = (root,q,cb) -> cb.like( cb.upper(root.get("tags")) , "%" + query.toUpperCase() + "%");

            // Combina as especificações de nome e tags usando uma disjunção (OR)
            Specification<Image> nameOrTagsLike = Specification.anyOf(nameLike,tagsLike);

            // Adiciona a especificação de nome ou tags semelhantes à especificação principal
            spec.and(nameOrTagsLike);
        }

        // Retorna a lista de imagens correspondentes aos critérios de pesquisa fornecidos
        return findAll(spec);
    }
}
