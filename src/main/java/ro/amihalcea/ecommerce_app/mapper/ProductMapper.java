package ro.amihalcea.ecommerce_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ro.amihalcea.ecommerce_app.dto.PhotoDTO;
import ro.amihalcea.ecommerce_app.dto.ProductDTO;
import ro.amihalcea.ecommerce_app.model.Photo;
import ro.amihalcea.ecommerce_app.model.Product;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PhotoMapper.class})
public abstract class ProductMapper {
    @Autowired
    private  PhotoMapper photoMapper;

    @Mapping(target = "photos", expression = "java(transformPhotosToModel(dto))")
    public abstract Product mapFromDTO(ProductDTO dto);

    @Mapping(target = "photos", expression = "java(transformPhotosToDTOs(product))")
    @Mapping(target = "mainPhoto", expression = "java(getMainPhoto(product))")
    public abstract ProductDTO mapFromModel(Product product);

    protected PhotoDTO getMainPhoto(Product product) {
        Photo photo = product
                .getPhotos()
                .stream()
                .filter(Photo::getIsMainPhoto)
                .findFirst()
                .orElse(null);
        return photoMapper.mapFromModel(photo);

    }

    protected List<Photo> transformPhotosToModel(ProductDTO dto) {
        return dto
                .getPhotos()
                .stream()
                .map(photoMapper::mapFromDTO)
                .toList();
    }

    protected List<PhotoDTO> transformPhotosToDTOs(Product product) {
        return product
                .getPhotos()
                .stream()
                .map(photoMapper::mapFromModel)
                .toList();
    }
}
