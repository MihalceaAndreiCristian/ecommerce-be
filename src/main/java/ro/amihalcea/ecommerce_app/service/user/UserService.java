package ro.amihalcea.ecommerce_app.service.user;

import ro.amihalcea.ecommerce_app.dto.UserDTO;
import ro.amihalcea.ecommerce_app.dto.UserDTOUpdate;

import java.util.List;

public interface UserService {

    UserDTO registerUser(UserDTO dto);
    void updateUser(UserDTOUpdate dto, int id);
    UserDTO getUser(String username, Integer id);
    List<UserDTO> getAllUsers();

}
