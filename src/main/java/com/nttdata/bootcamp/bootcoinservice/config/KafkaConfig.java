package com.nttdata.bootcamp.bootcoinservice.config;

import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.bootcamp.bootcoinservice.dto.TransactionDto;
import com.nttdata.bootcamp.bootcoinservice.utilities.GsonDeserializer;
import com.nttdata.bootcamp.bootcoinservice.utilities.JacksonSerializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;



@Configuration
public class KafkaConfig {

	@Bean
	public KafkaReceiver<String, TransactionDto> kafkaReceiver(ObjectMapper objectMapper) {
	    ReceiverOptions<String, TransactionDto> receiverOptions = ReceiverOptions.<String, TransactionDto>create()
	            .consumerProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092")
	            .consumerProperty("group.id", "myConsumerGroup")
                .subscription(Arrays.asList("transactionBootCoin"))
	            .withKeyDeserializer(new StringDeserializer())
	            .withValueDeserializer(new GsonDeserializer<>(TransactionDto.class));
	    return KafkaReceiver.create(receiverOptions);
	}

	@Bean
    public KafkaSender<String, TransactionDto> kafkaSender(ObjectMapper objectMapper) {
        SenderOptions<String, TransactionDto> senderOptions = SenderOptions.<String, TransactionDto>create()
                .producerProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092")
	            .producerProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class)

                .withValueSerializer(new JacksonSerializer<>(objectMapper));
        return KafkaSender.create(senderOptions);
    }

}