package ro.amihalcea.ecommerce_app.service.product;


import ro.amihalcea.ecommerce_app.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO getProduct(Integer productId);
    List<ProductDTO> getAllProducts();
    ProductDTO addProduct(ProductDTO productToBeAdded);
    ProductDTO updateProduct(ProductDTO newData, int productId);
}
