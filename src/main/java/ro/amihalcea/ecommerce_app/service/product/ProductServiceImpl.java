package ro.amihalcea.ecommerce_app.service.product;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.amihalcea.ecommerce_app.dto.PhotoDTO;
import ro.amihalcea.ecommerce_app.dto.ProductDTO;
import ro.amihalcea.ecommerce_app.exception.ProductNotFoundException;
import ro.amihalcea.ecommerce_app.mapper.ProductMapper;
import ro.amihalcea.ecommerce_app.repository.ProductRepository;
import ro.amihalcea.ecommerce_app.service.product.photo.PhotoService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        throw new ProductNotFoundException("Product not found by id '%s", productId);
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
    public ProductDTO updateProduct(ProductDTO newData,
                                    int productId) {
        var productFromDbDTO = getProduct(productId);
        productFromDbDTO.setLastUpdate(Timestamp.valueOf(LocalDateTime.now()));
        productFromDbDTO.setPrice(newData.getPrice() != null && newData.getPrice() != 0.0 ? newData.getPrice() :
                productFromDbDTO.getPrice());
        productFromDbDTO.setName(updateStringOrDismiss(newData.getName(), productFromDbDTO.getName()));
        productFromDbDTO.setDescription(updateStringOrDismiss(newData.getDescription(), productFromDbDTO.getDescription()));
        productFromDbDTO.setPhotos(validateProductPhotos(newData, productFromDbDTO));
        var productUpdated = mapper.mapFromDTO(productFromDbDTO);

        repository.save(productUpdated);


        return productFromDbDTO;
    }

    private String updateStringOrDismiss(String newValue,
                                         String existingValue) {
        if (newValue != null && !newValue.isEmpty()) {
            return newValue;
        }
        return existingValue;
    }

    private List<PhotoDTO> validateProductPhotos(ProductDTO updatedProduct,
                                                 ProductDTO productFromDb) {
        var newList = updatedProduct.getPhotos();
        var oldList = productFromDb.getPhotos();
        HashSet<PhotoDTO> existingElements = new HashSet<>(oldList);

        for (PhotoDTO p : newList) {
            if (!existingElements.contains(p)) {
                oldList.add(p);
            }

       }
        return existingElements.stream().toList();
    }
}
