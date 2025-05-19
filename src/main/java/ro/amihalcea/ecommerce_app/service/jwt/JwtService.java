package ro.amihalcea.ecommerce_app.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import ro.amihalcea.ecommerce_app.model.UserPrincipal;

public interface JwtService {

    String generateToken(UserPrincipal userPrincipal);
    String extractUsername(String token);
    boolean validateToken(String token, UserDetails userDetails);
}
