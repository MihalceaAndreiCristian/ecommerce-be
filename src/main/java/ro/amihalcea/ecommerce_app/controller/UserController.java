package ro.amihalcea.ecommerce_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import ro.amihalcea.ecommerce_app.dto.UserDTO;
import ro.amihalcea.ecommerce_app.service.user.UserService;
import ro.amihalcea.ecommerce_app.service.jwt.JwtService;

import java.util.List;

@RestController
public class UserController {


    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/users/register")
    public UserDTO register(@RequestBody UserDTO user) {
        return service.registerUser(user);
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
