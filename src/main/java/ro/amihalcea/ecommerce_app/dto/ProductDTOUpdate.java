package ro.amihalcea.ecommerce_app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;



@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter 
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTOUpdate extends ProductDTO{


    private List<String> removePhotoByKeys;
}
