import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "dish")
public class Dish extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public String description;

    @ManyToOne
    public Restaurant restaurant;

    public BigDecimal price;
}
