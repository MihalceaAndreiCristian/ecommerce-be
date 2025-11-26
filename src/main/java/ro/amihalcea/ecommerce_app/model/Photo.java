package ro.amihalcea.ecommerce_app.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name="photos")
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
    @Column(name = "is_main_photo")
    private Boolean isMainPhoto;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public String toString() {
        return "Photo{" +
                "photoId='" + photoId + '\'' +
                ", photoName='" + photoName + '\'' +
                ", extension='" + extension + '\'' +
                ", isMainPhoto=" + isMainPhoto +
                ", product=" + product +
                '}';
    }
}
