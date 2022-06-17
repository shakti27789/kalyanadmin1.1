package com.exchange.api.bean;

import java.util.Date;

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
@Table(name = "t_casino_betlock")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CasinoBetLoack {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "userid")
	private Integer userid;

	@Column(name = "tabletype")
	private String tabletype;

	@Column(name = "createdon")
	private Date createdon;
	
	

}
