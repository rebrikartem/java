package is.technologies.dto;

import lombok.Data;
import lombok.Value;

@Value
@Data
public class CreateStreetDto {
    private String name;
    private int index;

//    public CreateStreetDto(String name, int index) {
//        this.name = name;
//        this.index = index;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getIndex() {
//        return index;
//    }
//
//    public void setIndex(int index) {
//        this.index = index;
//    }
}
