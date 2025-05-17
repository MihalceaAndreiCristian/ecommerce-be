package ro.amihalcea.ecommerce_app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTOUpdate {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

}
