package ro.amihalcea.ecommerce_app.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="photos")
@ToString
public class Photo {

    @Id
    @Column(name = "photo_id")
    private String photoId;
    @Column(name = "name")
    private String photoName;
    @Column(name = "content")
    private byte[] content;
    @Column(name = "extension")
    private String extension;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
