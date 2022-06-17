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
@Table(name = "t_sessionbook")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXSessionBook {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "userid")
	private String userId;

	@Column(name = "pnlamount")
	private Double pnlAmount;

	@Column(name = "liblityamount")
	private Double libilityAmount;

	@Column(name = "minodds")
	private Double minOdds;

	@Column(name = "maxodds")
	private Double maxOdds;
}
