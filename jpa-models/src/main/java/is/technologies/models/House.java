package is.technologies.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_constr")
    private Date dateOfConstruct;

    @Column(name = "number_of_floors")
    private int numberOfFloors;

    @Column(name = "type", length = 50)
    @Enumerated(EnumType.STRING)
    private HouseType houseType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "street", updatable=false, insertable=false)
    @JsonIgnore
    private Street street;

    @Column(name="street", nullable = false)
    private long street_fk;

    @Column(name = "material")
    private String material;

    public House() { }

    public House(String name, Date dateOfConstruct, int numberOfFloors, HouseType houseType, String material) {
        this.name = name;
        this.dateOfConstruct = dateOfConstruct;
        this.numberOfFloors = numberOfFloors;
        this.houseType = houseType;
        this.material = material;
    }


    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public Street getStreet() {
        return street;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfConstruct(Date dateOfConstruct) {
        this.dateOfConstruct = dateOfConstruct;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public void setHouseType(HouseType houseType) {
        this.houseType = houseType;
    }

    public Date getDateOfConstruct() {
        return dateOfConstruct;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public HouseType getHouseType() {
        return houseType;
    }

    @Override
    public String toString() {
        return "House{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", dateOfConstruct=" + dateOfConstruct +
            ", numberOfFloors=" + numberOfFloors +
            ", houseType=" + houseType +
            '}';
    }

    public long getStreet_fk() {
        return street_fk;
    }

    public void setStreet_fk(long street_fk) {
        this.street_fk = street_fk;
    }
}
