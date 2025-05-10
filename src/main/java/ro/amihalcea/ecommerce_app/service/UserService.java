package ro.amihalcea.ecommerce_app.service;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.amihalcea.ecommerce_app.dto.UserDTO;

import ro.amihalcea.ecommerce_app.mapper.UserMapper;
import ro.amihalcea.ecommerce_app.model.User;
import ro.amihalcea.ecommerce_app.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {


    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepository repo,
                       PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.mapper = Mappers.getMapper(UserMapper.class);

    }

    @Transactional
    public UserDTO registerUser(UserDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        var userAlreadyRegistered = repo.findByUsernameOrEmail(dto.getUsername(), dto.getEmail());
        if (userAlreadyRegistered.isEmpty()) {
            var user = mapper.mapFromDto(dto);
            var usersaved = repo.save(user);
            if (usersaved != null) {
                return dto;
            }
            throw new RuntimeException(String.format("Issue while registering user %s.\n", dto.getUsername()));
        }
        throw new RuntimeException("User already exists.");
    }

    @Transactional
    public void updateUser(UserDTO dto,
                           int id) {
        repo
                .findById(id)
                .ifPresentOrElse(user -> {
                    UserDTO mappedUserFromDbToDTO = mapper.mapFromModel(user);
                    checkIfUserIsAllowed(mappedUserFromDbToDTO);
                    User userUpdatedToSave = mapper.mapFromDto(UserDTO.updateUserData(dto, mappedUserFromDbToDTO));
                    repo.saveAndFlush(userUpdatedToSave);
                    log.info("User saved successfully with id {}", id);
                }, () -> {
                    log.error("User not found by id '{}'", id);
                    throw new RuntimeException("User not found by id " + id);
                });
    }

    @Transactional
    public UserDTO getUser(String username,
                           Integer id) {

        Optional<User> user = Optional.empty();
        if (username == null || username.isEmpty()) {
            user = repo.findById(id);
        }
        if (id == null || id == 0) {
            user = repo.findByUsername(username);
        }
        if (user.isPresent()) {

            UserDTO resultUser = mapper.mapFromModel(user.get());
            checkIfUserIsAllowed(resultUser);
            return resultUser;
        }
        log.warn("User not present by username '{}' or id '{}'", username, id);
        throw new RuntimeException(String.format("User not present."));
    }


    public List<UserDTO> getAllUsers() {
        return repo
                .findAll()
                .stream()
                .map(mapper::mapFromModel)
                .toList();
    }

    private void checkIfUserIsAllowed(UserDTO dto){
        String usernameFromToken = ((UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()).getUsername();
        if (!dto.getUsername().equalsIgnoreCase(usernameFromToken)){
            throw new RuntimeException("User cannot access this data.");
        }

    }
}
