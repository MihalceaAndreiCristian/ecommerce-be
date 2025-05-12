package ro.amihalcea.ecommerce_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.amihalcea.ecommerce_app.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
