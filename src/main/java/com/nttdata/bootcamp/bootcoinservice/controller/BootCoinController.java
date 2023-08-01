package com.nttdata.bootcamp.bootcoinservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.bootcoinservice.documents.BootCoin;
import com.nttdata.bootcamp.bootcoinservice.dto.ExchangeRateDto;
import com.nttdata.bootcamp.bootcoinservice.dto.TransactionDto;
import com.nttdata.bootcamp.bootcoinservice.service.BootCoinService;

import reactor.core.publisher.Mono;



@RestController
@RequestMapping("/")
public class BootCoinController {

	@Autowired
    private BootCoinService bootCoinService;

	
	@PostMapping("/save")
    public Mono<ResponseEntity<BootCoin>> saveBootCoin(@RequestBody BootCoin bootCoin) {
        return bootCoinService.saveBootCoin(bootCoin)
                .map(savedBootCoin -> ResponseEntity.ok(savedBootCoin))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@PostMapping("/exchange-rate")
    public ResponseEntity<String> saveExchangeRate(@RequestBody ExchangeRateDto exchangeRate) {
		bootCoinService.saveExchangeRate(exchangeRate);
        return ResponseEntity.ok("Tasa de cambio guardada correctamente en Redis.");
    }

	@PostMapping("/buyBootCoin")
    public Mono<BootCoin> buyBootCoin(@RequestBody BootCoin bootCoin) {
        // solicitar la compra bootCoin
        return bootCoinService.buyCoinRequest(bootCoin);
    }

	@PostMapping("/acceptExchange")
    public Mono<TransactionDto> buyBootCoin(@RequestBody TransactionDto transactionDto) {
        // aceptar la compra bootCoin
        return bootCoinService.acceptExchange(transactionDto);
    }
}
