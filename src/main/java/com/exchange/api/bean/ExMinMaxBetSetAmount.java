package com.exchange.api.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Entity
@Table(name = "t_minmaxbetamount")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExMinMaxBetSetAmount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "amount")
	private Integer amount;

	@Column(name = "type")
	private String type;

	@Column(name = "isactive")
	private Boolean isActive;

}
