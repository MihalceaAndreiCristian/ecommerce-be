package ro.amihalcea.ecommerce_app.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import ro.amihalcea.ecommerce_app.dto.LoginDTO;
import ro.amihalcea.ecommerce_app.model.UserPrincipal;
import ro.amihalcea.ecommerce_app.service.JwtService;

import java.io.IOException;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ObjectMapper mapper;
    private static final String AUTHORIZATION = "Authorization";


    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService, ObjectMapper mapper) {
        this.authenticationManager = authenticationManager;
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
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword()));
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
        String token = jwtService.generateToken(((UserPrincipal) authResult.getPrincipal()).getUsername());
        log.info("Token generated -> {}", token);
        response.addHeader(AUTHORIZATION, token);
        response.setHeader(AUTHORIZATION, token);

    }
}
