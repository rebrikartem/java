package is.technologies.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import is.technologies.dto.CreateHouseDto;
import is.technologies.dto.HouseDto;
import is.technologies.models.House;
import is.technologies.repos.HouseRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepo houseRepo;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    @KafkaListener(id = "houseCreate", topics = "houseCreate")
    @SendTo
    public String saveHouse(String str) throws JsonProcessingException {
        CreateHouseDto houseDto = objectMapper.readValue(objectMapper.readValue(str, String.class), CreateHouseDto.class);
        House house = modelMapper.map(houseDto, House.class);

        house.setStreet_fk(houseDto.getStreetId());
        House createdHouse = houseRepo.save(house);

        HouseDto createdHouseDto = modelMapper.map(createdHouse, HouseDto.class);
        return objectMapper.writeValueAsString(createdHouseDto);
    }

    @KafkaListener(id = "houseDelete", topics = "houseDelete")
    @SendTo
    public String deleteById(String str) throws JsonProcessingException {
        Long id = objectMapper.readValue(objectMapper.readValue(str, String.class), Long.class);
        houseRepo.deleteById(id);
        return String.valueOf(id);
    }

    @KafkaListener(id = "houseGetById", topics = "houseGetById")
    @SendTo
    public String getById(String str) throws JsonProcessingException {
        Long id = Long.parseLong(objectMapper.readValue(str, String.class));
        var house = houseRepo.findById(id);
        if (house.isPresent()) {
            HouseDto houseDto = modelMapper.map(house.get(), HouseDto.class);
            return objectMapper.writeValueAsString(houseDto);
        }
        throw new EntityNotFoundException("Can't find house with id: " + id);
    }

    @KafkaListener(id = "houseGetAll", topics = "houseGetAll")
    @SendTo
    public String getAll() throws JsonProcessingException {
        List<HouseDto> streetDtoList = houseRepo
                .findAll().stream()
                .map((street -> modelMapper.map(street, HouseDto.class)))
                .collect(Collectors.toList());
        return objectMapper.writeValueAsString(streetDtoList);
    }
}
