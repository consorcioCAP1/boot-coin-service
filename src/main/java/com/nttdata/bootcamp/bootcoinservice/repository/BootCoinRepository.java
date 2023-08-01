package com.nttdata.bootcamp.bootcoinservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.nttdata.bootcamp.bootcoinservice.documents.BootCoin;

import reactor.core.publisher.Mono;

@Repository
public interface BootCoinRepository extends ReactiveMongoRepository<BootCoin, String>{

	Mono<BootCoin> findByNumberDocument(String numberDocument);


}
