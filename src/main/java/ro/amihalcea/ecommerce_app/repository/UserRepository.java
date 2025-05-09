package ro.amihalcea.ecommerce_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import ro.amihalcea.ecommerce_app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Query(value = "SELECT * FROM ecommerce.users AS u WHERE u.username= :username OR u.email= :email", nativeQuery = true)
    User findByUsernameOrEmail(@Param("username") String username,@Param("email") String email);

}
