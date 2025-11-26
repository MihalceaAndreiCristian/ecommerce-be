package ro.amihalcea.ecommerce_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter 
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Double price;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "last_update")
    private Timestamp lastUpdate;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Photo> photos;
}
