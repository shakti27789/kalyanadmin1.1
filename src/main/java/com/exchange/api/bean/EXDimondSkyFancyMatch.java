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
@Table(name = "t_daimondskyfancy")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXDimondSkyFancyMatch {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "skyfancyid")
	private String skyfancyid;

	@Column(name = "daimondfancyid")
	private String daimondfancyid;

}
