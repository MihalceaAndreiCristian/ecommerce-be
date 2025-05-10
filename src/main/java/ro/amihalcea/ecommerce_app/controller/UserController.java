package ro.amihalcea.ecommerce_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ro.amihalcea.ecommerce_app.dto.LoginDTO;
import ro.amihalcea.ecommerce_app.dto.UserDTO;
import ro.amihalcea.ecommerce_app.service.JwtService;
import ro.amihalcea.ecommerce_app.service.UserService;

import java.util.List;

@RestController
public class UserController {


    private final UserService service;
    private final AuthenticationManager manager;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService service,
                          AuthenticationManager manager,
                          JwtService jwtService) {
        this.service = service;
        this.manager = manager;
        this.jwtService = jwtService;
    }

    @PostMapping("/users/register")
    public UserDTO register(@RequestBody UserDTO user) {
        return service.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication =
                manager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        if (authentication.isAuthenticated()) {

            String token = jwtService.generateToken(loginDTO.getUsername());
            return ResponseEntity
                    .ok()
                    .header("Authorization", token)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatusCode.valueOf(401))
                .build();
    }

    @PatchMapping("/users/user/update/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody UserDTO dto,
                                           @PathVariable("id") int id) {
        service.updateUser(dto, id);
        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("/users/user")
    public ResponseEntity<UserDTO> getUserByIdOrUsername(@RequestParam(value = "username", required = false) String username,
                                                         @RequestParam(value = "userId", required = false) Integer id) {
        if ((username == null || username.isEmpty()) && (id == null || id == 0)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity.ok(service.getUser(username, id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(service.getAllUsers());
    }
}
