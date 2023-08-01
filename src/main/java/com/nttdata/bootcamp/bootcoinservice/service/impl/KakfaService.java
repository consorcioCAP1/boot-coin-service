package com.nttdata.bootcamp.bootcoinservice.service.impl;


import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.bootcamp.bootcoinservice.documents.BootCoin;
import com.nttdata.bootcamp.bootcoinservice.dto.TransactionDto;
import com.nttdata.bootcamp.bootcoinservice.repository.BootCoinRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;


@Slf4j
@Service
public class KakfaService {

	@Autowired
    private BootCoinRepository repository;
	
	private ObjectMapper objectMapper;
	
	@Autowired
	private KafkaReceiver<String, TransactionDto> kafkaReceiver;
    

    private String topicUpdateBalanceWallet = "transactionBootCoin";

//	@PostConstruct
//    public void startConsumeTopic() {
//    	 consumeTopics();
//    }
//
//    private void consumeTopics() {
//        kafkaReceiver.receive()
//            .doOnNext(record -> {
//                String topic = record.topic();
//                TransactionDto value = record.value();
//
//                if (topicUpdateBalanceWallet.equals(topic)) {
//                	consumeTopicTransactionBootCoin(value);
//                } else {
//                    log.warn("others topic: ", topic);
//                }
//            })
//            .subscribe();
//    }
//	public void consumeTopicTransactionBootCoin(TransactionDto value){
//		 System.out.println("Transaction received: " + value);
//	            
//    }


}
