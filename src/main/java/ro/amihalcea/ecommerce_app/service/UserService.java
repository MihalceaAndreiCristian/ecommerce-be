package ro.amihalcea.ecommerce_app.service;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.amihalcea.ecommerce_app.dto.UserDTO;

import ro.amihalcea.ecommerce_app.mapper.UserMapper;
import ro.amihalcea.ecommerce_app.repository.UserRepository;

@Service
public class UserService {


    private final UserRepository repo;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepository repo) {
        this.repo = repo;
        this.mapper= Mappers.getMapper(UserMapper.class);

    }

    public UserDTO registerUser(UserDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        var userAlreadyRegistered = repo.findByUsernameOrEmail(dto.getUsername(),dto.getEmail());
        if (userAlreadyRegistered == null){
            var user = mapper.mapFromDto(dto);
            var usersaved = repo.save(user);
            if ( usersaved != null) {
                return dto;
            }
            throw new RuntimeException(String.format("Issue while registering user %s.\n", dto.getUsername()));
        }
        throw new RuntimeException("User already exists.");
    }

}
