package ro.amihalcea.ecommerce_app.service.product.photo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.amihalcea.ecommerce_app.dto.PhotoDTO;
import ro.amihalcea.ecommerce_app.mapper.PhotoMapper;
import ro.amihalcea.ecommerce_app.model.Product;
import ro.amihalcea.ecommerce_app.repository.PhotoRepository;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoMapper photoMapper;
    private final PhotoRepository repository;

    @Autowired
    public PhotoServiceImpl(PhotoMapper photoMapper,
                            PhotoRepository repository) {
        this.photoMapper = photoMapper;
        this.repository = repository;
    }

    @Override
    public List<PhotoDTO> addPhotos(List<PhotoDTO> photoDTO) {
        var photoModelToSave = photoDTO
                .stream()
                .map(photoMapper::mapFromDTO)
                .toList();
        var photosSaved = repository.saveAll(photoModelToSave);
        return photosSaved
                .stream()
                .map(photoMapper::mapFromModel)
                .toList();
    }

    @Override
    public List<PhotoDTO> getAllPhotosByProductId(Integer productId) {
        return repository
                .findAllByProductId(productId)
                .stream()
                .map(photoMapper::mapFromModel)
                .toList();
    }

    @Override
    @Transactional
    public boolean deletePhotosByProduct(Product product) {
        int rowsDeleted = repository.deletePhotoByProductId(product.getId());
        boolean result = rowsDeleted > 0;
        if (result){
            log.info("{} photos deleted with success.", rowsDeleted);
        }else {
            log.warn("No photos has been deleted.");
        }

        return result;
    }

    @Override
    public void deletePhotosInBatch(Collection<String> photosKeys) {
        repository.deleteAllById(photosKeys);
    }
}
