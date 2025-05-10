package ro.amihalcea.ecommerce_app.service.user;

import ro.amihalcea.ecommerce_app.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO registerUser(UserDTO dto);
    void updateUser(UserDTO dto, int id);
    UserDTO getUser(String username, Integer id);
    List<UserDTO> getAllUsers();

}
