package com.exchange;

import lombok.Data;

@Data
public class Hardware {

	public Hardware(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
	}
	private String name;
	private int quantity;
}
