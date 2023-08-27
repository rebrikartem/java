package is.technologies.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import is.technologies.dto.CreateHouseDto;
import is.technologies.dto.HouseDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final ReplyingKafkaTemplate<String, String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public List<HouseDto> findAll() {
        try {
            ConsumerRecord<String, String> consumerRecord = consumerRecord("houseGetAll", null);
            return objectMapper.readValue(consumerRecord.value(), new TypeReference<List<HouseDto>>() {
            });
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
        }
    }

    public HouseDto findById(long id) {
        try {
            ConsumerRecord<String, String> consumerRecord = consumerRecord("houseGetById", String.valueOf(id));
            return objectMapper.readValue(consumerRecord.value(), new TypeReference<HouseDto>() {});
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
        }
    }

    public HouseDto create(CreateHouseDto houseDto) {
        try {
            ConsumerRecord<String, String> consumerRecord = consumerRecord(
                    "houseCreate",
                    objectMapper.writeValueAsString(houseDto)
            );
            return objectMapper.readValue(consumerRecord.value(), new TypeReference<HouseDto>() {});
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
        }
    }

    public void delete(long id) {
        try {
            ConsumerRecord<String, String> consumerRecord = consumerRecord("houseDelete", String.valueOf(id));
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
