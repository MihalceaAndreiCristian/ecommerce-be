package ro.amihalcea.ecommerce_app.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ro.amihalcea.ecommerce_app.dto.LoginDTO;
import ro.amihalcea.ecommerce_app.model.UserPrincipal;
import ro.amihalcea.ecommerce_app.service.jwt.JwtService;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;
    private final ObjectMapper mapper;


    public AuthenticationFilter(AuthenticationProvider authenticationProvider, JwtService jwtService, ObjectMapper mapper) {
        this.authenticationProvider = authenticationProvider;
        this.jwtService = jwtService;
        this.mapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        LoginDTO loginData = null;
        try {
            loginData = getCredentialsFromRequest(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword()));
    }

    private LoginDTO getCredentialsFromRequest(HttpServletRequest request) throws IOException {
        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            username = request.getHeader("username");
            password = request.getHeader("password");
        }
        if (username == null || password == null) {
            var reader = request.getReader();
            String line;
            StringBuilder inputBody = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                inputBody.append(line);
            }

            LoginDTO userDTO = mapper.readValue(inputBody.toString(), LoginDTO.class);

            if (userDTO != null) {
                username = userDTO.getUsername();
                password = userDTO.getPassword();
            }

        }
        return new LoginDTO(username, password);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String token = jwtService.generateToken(((UserPrincipal) authResult.getPrincipal()));
        response.addHeader(AUTHORIZATION, token);
        response.setHeader(AUTHORIZATION, token);
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age","3600");
        response.addHeader("Access-Control-Allow-Headers",
                "X-PINGOTHER, Content-Type, X-Requested-With, accept*,Origin, Access-Control-Request-Nethod, Access-Control-Request-Headers, Authorization");
        response.addHeader("Access-Control-Expose-Headers",AUTHORIZATION);
    }
}
