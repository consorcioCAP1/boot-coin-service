package com.nttdata.bootcamp.bootcoinservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDto {

	private Double sellRate;
	private Double buyRate;

}
