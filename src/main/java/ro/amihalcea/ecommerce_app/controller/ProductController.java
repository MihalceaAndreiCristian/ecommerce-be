package ro.amihalcea.ecommerce_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.amihalcea.ecommerce_app.dto.ProductDTO;
import ro.amihalcea.ecommerce_app.dto.ProductDTOUpdate;
import ro.amihalcea.ecommerce_app.service.product.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") int id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PostMapping("/product/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productToSave) {
        return ResponseEntity.ok(productService.addProduct(productToSave));
    }

    @PatchMapping("/product/edit/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTOUpdate updatedProduct,
                                                    @PathVariable("productId") int productId){
        return ResponseEntity.ok(productService.updateProduct(updatedProduct,productId));
    }

    @DeleteMapping("/product/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") int productId){
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }
}
