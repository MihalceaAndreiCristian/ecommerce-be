package ro.amihalcea.ecommerce_app.service.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.amihalcea.ecommerce_app.dto.PhotoDTO;
import ro.amihalcea.ecommerce_app.dto.ProductDTO;
import ro.amihalcea.ecommerce_app.dto.ProductDTOUpdate;
import ro.amihalcea.ecommerce_app.exception.ProductNotFoundException;
import ro.amihalcea.ecommerce_app.mapper.ProductMapper;
import ro.amihalcea.ecommerce_app.model.Product;
import ro.amihalcea.ecommerce_app.repository.ProductRepository;
import ro.amihalcea.ecommerce_app.service.product.photo.PhotoService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final PhotoService photoService;

    @Autowired
    public ProductServiceImpl(ProductRepository repository,
                              ProductMapper mapper,
                              PhotoService photoService) {
        this.repository = repository;
        this.mapper = mapper;
        this.photoService = photoService;
    }

    @Override
    public ProductDTO getProduct(Integer productId) {
        var productFromDB = repository.findById(productId);
        if (productFromDB.isPresent()) {
            return mapper.mapFromModel(productFromDB.get());
        }
        throw new ProductNotFoundException("Product not found by id '%s'", productId);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return repository
                .findAll()
                .stream()
                .map(mapper::mapFromModel)
                .toList();
    }

    @Override
    @Transactional
    public ProductDTO addProduct(ProductDTO productToBeAdded) {
        productToBeAdded.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        var photos = productToBeAdded.getPhotos();
        var productToSave = mapper.mapFromDTO(productToBeAdded);
        var productSaved = repository.save(productToSave);

        if (photos == null){
            return mapper.mapFromModel(productSaved);
        }

        photos = photos.stream()
                .map(photo -> {
                    photo.setProductId(productSaved.getId());
                    photo.setPhotoId(UUID.randomUUID().toString());
                    return photo;
                })
                .toList();
        var photosSaved = photoService.addPhotos(photos);
        var productToReturn = mapper.mapFromModel(productSaved);
        productToReturn.setPhotos(photosSaved);
        return productToReturn;
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(ProductDTOUpdate newData,
                                    int productId) {
        ProductDTO productFromDbDTO = getProduct(productId);
        productFromDbDTO.setLastUpdate(Timestamp.valueOf(LocalDateTime.now()));
        productFromDbDTO.setPrice(newData.getPrice() != null && newData.getPrice() != 0.0 ? newData.getPrice() :
                productFromDbDTO.getPrice());
        productFromDbDTO.setName(updateStringOrDismiss(newData.getName(), productFromDbDTO.getName()));
        productFromDbDTO.setDescription(updateStringOrDismiss(newData.getDescription(), productFromDbDTO.getDescription()));

        // todo salvat pozele si verificat cum sa le scoatem din frontend pe cele la care facem delete
        Product productUpdated = mapper.mapFromDTO(productFromDbDTO);
        // save updated photos too
        log.info("Saving product {}", productUpdated);
        repository.save(productUpdated);

        productFromDbDTO.setPhotos(validateProductPhotos(newData));

        return productFromDbDTO;
    }

    @Override
    @Transactional
    public void deleteProduct(int productId) {
        var productFound = repository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Not found " +
                "by id %s", productId));

        photoService.deletePhotosByProduct(productFound);
        repository.delete(productFound);

        log.info("Product with id: {} deleted successfully.",productId);
    }

    private String updateStringOrDismiss(String newValue,
                                         String existingValue) {
        if (newValue != null && !newValue.isEmpty()) {
            return newValue;
        }
        return existingValue;
    }

    @Transactional
    private List<PhotoDTO> validateProductPhotos(ProductDTOUpdate updatedProduct) {
        var removePhotoByKeys = updatedProduct.getRemovePhotoByKeys();
        if (removePhotoByKeys !=null && !removePhotoByKeys.isEmpty()){
            photoService.deletePhotosInBatch(removePhotoByKeys);
        }

        var photosToSave  = updatedProduct.getPhotos();
        List<PhotoDTO> photosSaved = new ArrayList<>();
        if (photosToSave!=null && !photosToSave.isEmpty()){
            photosToSave = photosSaved.stream()
                    .map(photo -> {
                        photo.setProductId(updatedProduct.getId());
                        photo.setPhotoId(UUID.randomUUID().toString());
                        return photo;
                    })
                    .toList();
            photosSaved = photoService.addPhotos(photosToSave);
        }

        return photosToSave;
    }
}
