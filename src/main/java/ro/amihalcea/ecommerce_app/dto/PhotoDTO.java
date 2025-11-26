package ro.amihalcea.ecommerce_app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter 
@Setter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhotoDTO {

    private String photoId;
    private String photoName;
    private String content;
    private String extension;
    private Integer productId;
    private Boolean isMainPhoto;

    @Override
    public String toString() {
        return "PhotoDTO{" +
                "photoId='" + photoId + '\'' +
                ", photoName='" + photoName + '\'' +
                ", extension='" + extension + '\'' +
                ", productId=" + productId +
                ", isMainPhoto=" + isMainPhoto +
                '}';
    }
}
