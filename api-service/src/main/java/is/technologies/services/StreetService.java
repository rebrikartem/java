package is.technologies.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import is.technologies.dto.CreateStreetDto;
import is.technologies.dto.StreetDto;
import is.technologies.models.User;
import is.technologies.repos.UserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class StreetService {

    private final ReplyingKafkaTemplate<String, String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepo;

    public List<StreetDto> findAll() {
        try {
            ConsumerRecord<String, String> consumerRecord;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))) {
                Optional<User> user = userRepo.findById(((User) auth.getPrincipal()).getId());
                consumerRecord = consumerRecord("streetGetById", String.valueOf(user.get().getStreet().getId()));
                return Collections.singletonList(
                    objectMapper.readValue(consumerRecord.value(), StreetDto.class));
            } else {
                consumerRecord = consumerRecord("streetGetAll", null);
                return objectMapper.readValue(consumerRecord.value(), new TypeReference<List<StreetDto>>() { });
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
        }
    }

    public StreetDto findById(Long id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            ConsumerRecord<String, String> consumerRecord;
            if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))) {
                Optional<User> user = userRepo.findById(((User) auth.getPrincipal()).getId());
                if (user.get().getStreet().getId() != id) {
                    throw new Exception("хихи");
                }
            }

            consumerRecord = consumerRecord("streetGetById", id.toString());
            return objectMapper.readValue(consumerRecord.value(), StreetDto.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
        }
    }

    public StreetDto create(CreateStreetDto streetDto) {
        try {
            ConsumerRecord<String, String> consumerRecord = consumerRecord("streetCreate", objectMapper.writeValueAsString(streetDto));
            return objectMapper.readValue(consumerRecord.value(), StreetDto.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
        }
    }

    public StreetDto update(StreetDto streetDto) {
        try {
            ConsumerRecord<String, String> consumerRecord = consumerRecord("streetUpdate", objectMapper.writeValueAsString(streetDto));
            return objectMapper.readValue(consumerRecord.value(), StreetDto.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
        }
    }

    public void delete(Long id) {
        try {
            ConsumerRecord<String, String> consumerRecord = consumerRecord("streetDeleteById", String.valueOf(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
        }
    }

    private ConsumerRecord<String, String> consumerRecord(String topic, String value) throws ExecutionException, InterruptedException, TimeoutException {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, value);
        RequestReplyFuture<String, String, String> replyFuture = kafkaTemplate.sendAndReceive(record);
        return replyFuture.get(3, TimeUnit.SECONDS);
    }
}
