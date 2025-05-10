package ro.amihalcea.ecommerce_app.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String role;


    public static UserDTO updateUserData(UserDTO receivedFromRequest,UserDTO receivedFromDB){
        receivedFromDB.setEmail(receivedFromRequest.getEmail());
        receivedFromDB.setFirstName(receivedFromRequest.getFirstName());
        receivedFromDB.setLastName(receivedFromRequest.getLastName());
        return receivedFromDB;
    }
}
