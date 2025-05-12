package ro.amihalcea.ecommerce_app.service.product.photo;

import ro.amihalcea.ecommerce_app.dto.PhotoDTO;

import java.util.List;

public interface PhotoService {

    List<PhotoDTO> addPhotos(List<PhotoDTO> photoDTO);
    List<PhotoDTO> getAllPhotosByProductId(Integer productId);
}
