package ro.amihalcea.ecommerce_app.service.product.photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.amihalcea.ecommerce_app.dto.PhotoDTO;
import ro.amihalcea.ecommerce_app.mapper.PhotoMapper;
import ro.amihalcea.ecommerce_app.repository.PhotoRepository;

import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService{

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
        var photoModelToSave = photoDTO.stream().map(photoMapper::mapFromDTO).toList();
        var photosSaved = repository.saveAll(photoModelToSave);
        return photosSaved.stream().map(photoMapper::mapFromModel).toList();
    }

    @Override
    public List<PhotoDTO> getAllPhotosByProductId(Integer productId) {
        return repository.findAllByProductId(productId).stream().map(photoMapper::mapFromModel).toList();
    }
}
