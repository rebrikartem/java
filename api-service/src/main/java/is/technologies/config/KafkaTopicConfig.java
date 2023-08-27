package is.technologies.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.converter.ByteArrayJsonMessageConverter;

@Configuration
@EnableAutoConfiguration
@EnableKafka
public class KafkaTopicConfig {

    @Bean
    ReplyingKafkaTemplate<String, String, String> template(
            ProducerFactory<String, String> pf,
            ConcurrentKafkaListenerContainerFactory<String, String> factory) {

        ConcurrentMessageListenerContainer<String, String> replyContainer =
                factory.createContainer("kReplies");
        replyContainer.getContainerProperties().setGroupId("repliesGroup");
        ReplyingKafkaTemplate<String, String, String> template =
                new ReplyingKafkaTemplate<>(pf, replyContainer);
        template.setMessageConverter(new ByteArrayJsonMessageConverter());
        template.setDefaultTopic("kRequests");
        return template;
    }

//    @Bean
//    ReplyingKafkaTemplate<String, Long, String> replyingTemplateStreet(
//            ProducerFactory<String, Long> pf,
//            ConcurrentKafkaListenerContainerFactory<String, String> factory) {
//
//        ConcurrentMessageListenerContainer<String, String> replyContainer =
//                factory.createContainer("kRepliesLong");
//        replyContainer.getContainerProperties().setGroupId("kRepliesLongGroup");
//        ReplyingKafkaTemplate<String, Long, String> template =
//                new ReplyingKafkaTemplate<>(pf, replyContainer);
//        template.setMessageConverter(new ByteArrayJsonMessageConverter());
//        template.setDefaultTopic("kRequestsLong");
//        return template;
//    }
}
