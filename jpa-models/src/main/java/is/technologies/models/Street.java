package is.technologies.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "streets")
public class Street {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int index;

    @OneToMany(mappedBy = "street", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<House> houses;

    @JsonIgnore
    @OneToOne(mappedBy = "street")
    private User user;

    public Street() { }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public Street(String name, int index) {
        this.name = name;
        this.index = index;
        houses = new ArrayList<>();
    }

    public void addHouse(House house) {
        house.setStreet(this);
        houses.add(house);
    }

    public void removeHouse(House house) {
        houses.remove(house);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Street street = (Street) o;
        return id == street.id && index == street.index && name.equals(street.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, index);
    }

    @Override
    public String toString() {
        return "Street{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", index=" + index +
            ", houses=" + houses +
            '}';
    }
}
