package ro.amihalcea.ecommerce_app.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class LoginDTO {

    private String username;
    private String password;
}
