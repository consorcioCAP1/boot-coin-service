package com.nttdata.bootcamp.bootcoinservice.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bootCoinWallet")
public class BootCoin {

	@Id
	private String id;
	private String numberDocument;
	private String typeDocument;
	private String numberPhone;
	private String email;
	private Integer numberCoins;
	//para cuando se solicite una compra
	private Integer numberCoinsBuy;
	private String typeBuy;
	private Boolean isRequested;
	private String numberAccountTransfer;
}
