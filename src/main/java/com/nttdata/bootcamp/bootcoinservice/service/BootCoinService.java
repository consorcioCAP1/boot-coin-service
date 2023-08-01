package com.nttdata.bootcamp.bootcoinservice.service;

import com.nttdata.bootcamp.bootcoinservice.documents.BootCoin;
import com.nttdata.bootcamp.bootcoinservice.dto.ExchangeRateDto;
import com.nttdata.bootcamp.bootcoinservice.dto.TransactionDto;

import reactor.core.publisher.Mono;

public interface BootCoinService {
	public Mono<BootCoin> saveBootCoin(BootCoin bootCoin);	
	public void saveExchangeRate(ExchangeRateDto exchangeRate);
	public Mono<BootCoin> buyCoinRequest(BootCoin bootCoin);
	public Mono<TransactionDto> acceptExchange(TransactionDto transactionDto);
}

