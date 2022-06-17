package com.exchange.api.bean;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EXCommissionFromDealer {

	@NotNull
	private BigDecimal oddsloss;

	@NotNull
	private BigDecimal fancyloss;

	@NotNull
	private String fancylosstype;

	@NotNull
	private String oddslosstype;

	@NotNull
	private BigDecimal oddswin;

	@NotNull
	private BigDecimal fancywin;

	@NotNull
	private String fancywintype;

	@NotNull
	private String oddswintype;

}
