import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "restaurant")
public class Restaurant extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public String proprietary;

    public String vatNumber;

    @ManyToOne
    public Location localization;

    @CreationTimestamp
    public Date createdAt;

    @UpdateTimestamp
    public Date updatedAt;
}
