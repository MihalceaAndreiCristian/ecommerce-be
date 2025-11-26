package ro.amihalcea.ecommerce_app.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter 
@Setter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends UserDTOUpdate{
    private String password;

    public static UserDTO updateUserData(UserDTOUpdate receivedFromRequest,UserDTO receivedFromDB){
        receivedFromDB.setEmail(receivedFromRequest.getEmail());
        receivedFromDB.setFirstName(receivedFromRequest.getFirstName());
        receivedFromDB.setLastName(receivedFromRequest.getLastName());
        return receivedFromDB;
    }
}
