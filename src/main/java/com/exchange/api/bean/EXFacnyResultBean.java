package com.exchange.api.bean;

import lombok.Data;

@Data
public class EXFacnyResultBean {

	private Double netpnl;
	private Double stack;
	private Double admprtnership = 0.0;
	private Double sbaprtnership = 0.0;
	private Double sumprtnership = 0.0;
	private Double masprtnership = 0.0;
	private Double dealprtnership = 0.0;
	private Double fancyloss;
	private String userchain;
	private Integer iduser;

}
