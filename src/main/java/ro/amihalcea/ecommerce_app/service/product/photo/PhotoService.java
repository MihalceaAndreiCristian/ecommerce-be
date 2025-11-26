package ro.amihalcea.ecommerce_app.service.product.photo;

import ro.amihalcea.ecommerce_app.dto.PhotoDTO;
import ro.amihalcea.ecommerce_app.model.Product;

import java.util.Collection;
import java.util.List;

public interface PhotoService {

    List<PhotoDTO> addPhotos(List<PhotoDTO> photoDTO);
    List<PhotoDTO> getAllPhotosByProductId(Integer productId);
    boolean deletePhotosByProduct(Product product);
    void deletePhotosInBatch(Collection<String> photosKeys);
}
