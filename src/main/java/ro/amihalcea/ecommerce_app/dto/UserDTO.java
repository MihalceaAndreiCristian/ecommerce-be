package ro.amihalcea.ecommerce_app.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

}
