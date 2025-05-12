package ro.amihalcea.ecommerce_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.amihalcea.ecommerce_app.dto.PhotoDTO;
import ro.amihalcea.ecommerce_app.model.Photo;
import ro.amihalcea.ecommerce_app.model.Product;
import ro.amihalcea.ecommerce_app.repository.ProductRepository;

import java.util.Base64;


@Mapper(componentModel = "spring",imports = {ProductRepository.class})
@Component
public abstract class PhotoMapper {

    @Autowired
    private ProductRepository productRepository;


    @Mapping(target = "product", expression = "java(getProductById(dto.getProductId()))")
    @Mapping(target = "content", expression = "java(decodeContent(dto))")
    public abstract Photo mapFromDTO(PhotoDTO dto);


    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "content", expression = "java(encodeContent(photo))")
    public abstract PhotoDTO mapFromModel(Photo photo);

    protected Product getProductById(Integer productId){
        if (productId == null || productId ==0){
            return null;
        }
        return productRepository.findById(productId).orElse(null);
    }

    protected byte[] decodeContent(PhotoDTO dto){
        String content = dto.getContent();
        if (content == null || content.isEmpty()){
            return null;
        }
        return Base64.getDecoder().decode(dto.getContent());
    }

    protected String encodeContent(Photo photo){
        byte[] content = photo.getContent();
        if (content == null || content.length == 0){
            return null;
        }
        return Base64.getEncoder().encodeToString(photo.getContent());
    }
}
