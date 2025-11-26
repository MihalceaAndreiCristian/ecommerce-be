package ro.amihalcea.ecommerce_app.service.product;


import ro.amihalcea.ecommerce_app.dto.ProductDTO;
import ro.amihalcea.ecommerce_app.dto.ProductDTOUpdate;

import java.util.List;

public interface ProductService {

    ProductDTO getProduct(Integer productId);
    List<ProductDTO> getAllProducts();
    ProductDTO addProduct(ProductDTO productToBeAdded);
    ProductDTO updateProduct(ProductDTOUpdate  newData, int productId);

    void deleteProduct(int productId);
}
