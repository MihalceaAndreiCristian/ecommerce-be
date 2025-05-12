package ro.amihalcea.ecommerce_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.amihalcea.ecommerce_app.model.Photo;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM ecommerce.photos AS p WHERE p.product_id= :productId")
    List<Photo> findAllByProductId(@Param("productId") int productId);
}
