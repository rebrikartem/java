package is.technologies.dto;

import is.technologies.models.HouseType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Date;

public class CreateHouseDto {

    private long id;
    private long streetId;
    private String name;
    private Date dateOfConstruct;
    private int numberOfFloors;
    private HouseType houseType;
    private String material;

    public CreateHouseDto(long id, long streetId, String name, Date dateOfConstruct, int numberOfFloors,
        HouseType houseType, String material) {
        this.id = id;
        this.streetId = streetId;
        this.name = name;
        this.dateOfConstruct = dateOfConstruct;
        this.numberOfFloors = numberOfFloors;
        this.houseType = houseType;
        this.material = material;
    }

    public long getStreetId() {
        return streetId;
    }

    public void setStreetId(long streetId) {
        this.streetId = streetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfConstruct() {
        return dateOfConstruct;
    }

    public void setDateOfConstruct(Date dateOfConstruct) {
        this.dateOfConstruct = dateOfConstruct;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public HouseType getHouseType() {
        return houseType;
    }

    public void setHouseType(HouseType houseType) {
        this.houseType = houseType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
