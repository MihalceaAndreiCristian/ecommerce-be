package ro.amihalcea.ecommerce_app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhotoDTO {

    private String photoId;
    private String photoName;
    private String content;
    private String extension;
    private Integer productId;
    private Boolean isMainPhoto;

}
