package com.nttdata.bootcamp.bootcoinservice.service.impl;

import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.bootcamp.bootcoinservice.documents.BootCoin;
import com.nttdata.bootcamp.bootcoinservice.dto.ExchangeRateDto;
import com.nttdata.bootcamp.bootcoinservice.dto.TransactionDto;
import com.nttdata.bootcamp.bootcoinservice.repository.BootCoinRepository;
import com.nttdata.bootcamp.bootcoinservice.service.BootCoinService;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;


@Service
public class BootCoinServiceImpl implements BootCoinService{

	@Autowired
    private BootCoinRepository repository;

	@Autowired
	private ReactiveRedisTemplate<String, ExchangeRateDto> redisTemplate;

	@Autowired
    private KafkaSender<String, TransactionDto> kafkaSender;

	@Override
	public Mono<BootCoin> saveBootCoin(BootCoin bootCoin){
        return repository.save(bootCoin);
    }

	@Override
    public Mono<BootCoin> buyCoinRequest(BootCoin bootCoin) {
        // Buscar el BootCoin por su número de documento
        return repository.findByNumberDocument(bootCoin.getNumberDocument())
            .switchIfEmpty(Mono.error(new RuntimeException(
            		"No se encuentra Monedero Coin con documento: " + bootCoin.getNumberDocument())))
            .flatMap(existingBootCoin -> {
                // Actualizar los campos necesarios
                existingBootCoin.setNumberCoinsBuy(bootCoin.getNumberCoinsBuy());
                existingBootCoin.setTypeBuy(bootCoin.getTypeBuy());
                existingBootCoin.setIsRequested(true);
                existingBootCoin.setNumberAccountTransfer(bootCoin.getNumberAccountTransfer());
                // Guardar los cambios en la base de datos
                return repository.save(existingBootCoin);
            });
    }	

	@Override
	public Mono<TransactionDto> acceptExchange(TransactionDto transactionDto) {
	    // Buscar otro usuario que haya solicitado la venta de BootCoin
	    return repository.findByNumberDocument(transactionDto.getNumberDocumentSeller())
            .switchIfEmpty(Mono.error(new RuntimeException(
                    "No se encuentra Monedero Coin con documento: " 
                    		+ transactionDto.getNumberDocumentSeller())))
            .flatMap(existingBootCoin -> {
                if (existingBootCoin.getNumberCoins() >= transactionDto.getAmountCoin()) {
                    // Actualizar los campos necesarios
                    transactionDto.setNumberTrasaction(generateTransactionNumber());
                    return getExchangeRate()
                            .map(exchangeRateDto -> {
                                transactionDto.setSellRate(exchangeRateDto.getSellRate());
                                transactionDto.setBuyRate(exchangeRateDto.getBuyRate());
                                return transactionDto;
                            })
                            .doOnNext(updatedTransactionDto -> {
                                // Enviar el objeto TransactionDto al topic 'transactionBootCoin'
                                SenderRecord<String, TransactionDto, Void> record = SenderRecord.create(
                                        new ProducerRecord<>("transactionBootCoin", updatedTransactionDto), null);
                                kafkaSender.send(Mono.just(record)).subscribe();
                            });
                } else {
                    return Mono.error(new RuntimeException(
                            "Vendedor No tiene monedas suficientes: " 
                            		+ transactionDto.getNumberDocumentSeller()));
                }
            });
	}

	public Mono<ExchangeRateDto> getExchangeRate() {
        return redisTemplate.opsForValue().get("exchange_rate");
    }

    @Override
    public void saveExchangeRate(ExchangeRateDto exchangeRate) {
        redisTemplate.opsForValue().set("exchange_rate", exchangeRate).subscribe();
    }
	
    private String generateTransactionNumber() {
        // Genera un número de transacción único
        return UUID.randomUUID().toString();
    }
}
