package com.exchange.api.bean;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data

public class EXUserDemo { 



	@NotNull
	private String username;


	@NotNull
//	@Column(name = "userid", unique = true)
	private String userid;


}
