package is.technologies.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import is.technologies.dto.CreateStreetDto;
import is.technologies.dto.StreetDto;
import is.technologies.models.Street;
import is.technologies.repos.StreetRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StreetService {

    private final StreetRepo streetRepo;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    @KafkaListener(id = "streetCreate", topics = "streetCreate")
    @SendTo
    public String save(String str) throws JsonProcessingException {
        CreateStreetDto createStreetDto = objectMapper.readValue(objectMapper.readValue(str, String.class), CreateStreetDto.class);
        Street street = modelMapper.map(createStreetDto, Street.class);
        Street savedStreet = streetRepo.save(street);
        StreetDto savedStreetDto = modelMapper.map(savedStreet, StreetDto.class);
        return objectMapper.writeValueAsString(savedStreetDto);
    }

    @KafkaListener(id = "streetDeleteById", topics = "streetDeleteById")
    @SendTo
    public String deleteById(String str) throws JsonProcessingException {
        Long id = objectMapper.readValue(objectMapper.readValue(str, String.class), Long.class);
        streetRepo.deleteById(id);
        return id.toString();
    }

    public void deleteAll() {
        streetRepo.deleteAll();
    }

    @KafkaListener(id = "streetUpdate", topics = "streetUpdate")
    @SendTo
    public String update(String str) throws JsonProcessingException {
        StreetDto streetDto = objectMapper.readValue(objectMapper.readValue(str, String.class), StreetDto.class);
        Street street = streetRepo.findById(streetDto.getId()).orElse(null);
        street.setName(streetDto.getName());
        street.setIndex(streetDto.getIndex());
        Street savedStreet = streetRepo.saveAndFlush(street);
        StreetDto savedStreetDto = modelMapper.map(savedStreet, StreetDto.class);
        return objectMapper.writeValueAsString(savedStreetDto);
    }

    @KafkaListener(id = "streetAll", topics = "streetGetAll")
    @SendTo
    public String streetGetAll() throws JsonProcessingException {
         List<StreetDto> streetDtoList = streetRepo
                 .findAll().stream()
                 .map((street -> modelMapper.map(street, StreetDto.class)))
                 .collect(Collectors.toList());
         return objectMapper.writeValueAsString(streetDtoList);
    }

    @KafkaListener(id = "street", topics = "streetGetById")
    @SendTo
    public String getById(String id) throws JsonProcessingException {
        Street street = streetRepo
            .findById(Long.parseLong(objectMapper.readValue(id, String.class)))
            .orElseThrow(() -> new NotFoundException("Can't find street with id: " + id));

        return objectMapper.writeValueAsString(modelMapper.map(street, StreetDto.class));
    }

    @KafkaListener(id = "testStreet", topics = "testStreetTopic")
    @SendTo
    public String testString(String id) {
        System.out.println(" --- HERE --- ");
        return "abobuuusss";
    }
}