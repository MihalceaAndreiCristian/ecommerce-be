package ro.amihalcea.ecommerce_app.controller;

import jakarta.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.amihalcea.ecommerce_app.dto.LoginDTO;
import ro.amihalcea.ecommerce_app.dto.UserDTO;
import ro.amihalcea.ecommerce_app.service.JwtService;
import ro.amihalcea.ecommerce_app.service.UserService;

@RestController
public class UserController {


    private final UserService service;
    private final AuthenticationManager manager;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService service, AuthenticationManager manager, JwtService jwtService) {
        this.service = service;
        this.manager = manager;
        this.jwtService = jwtService;
    }

    @PostMapping ("/users/register")
    public UserDTO register(@RequestBody UserDTO user){

        return service.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){

        Authentication authentication =
                manager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword()));
        if (authentication.isAuthenticated()){

            String token = jwtService.generateToken(loginDTO.getUsername());
            return ResponseEntity
                    .ok()
                    .header("Authorization", token)
                    .build();

        }else {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(401))
                    .build();
        }

    }
}
