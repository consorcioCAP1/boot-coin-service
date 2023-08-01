package com.nttdata.bootcamp.bootcoinservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
		
	//datos vendedor
	private String saleType;
	private String numberDocumentSeller;
	private String numberAccountSeller;
	private String numberPhoneSeller;
	//datos comprador 
	private String buyType;
	private String numberDocumentBuyer;
	private String numberAccountBuyer;
	private String numberPhoneBuyer;
	
	//datos de transaccion
	private Double sellRate;
	private Double buyRate;
	private Integer amountCoin;
	private String numberTrasaction;
}
