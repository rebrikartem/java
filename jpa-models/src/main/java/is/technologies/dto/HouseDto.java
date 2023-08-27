package is.technologies.dto;

import is.technologies.models.HouseType;
import lombok.Data;
import lombok.Setter;

import java.util.Date;

@Setter
@Data
public class HouseDto {
    private long id;

    private String name;

    private Date dateOfConstruct;

    private int numberOfFloors;

    private HouseType houseType;

    private String material;
}
