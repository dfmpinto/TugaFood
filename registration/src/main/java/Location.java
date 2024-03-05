import jakarta.persistence.*;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Double latitude;

    public Double longitude;
}
