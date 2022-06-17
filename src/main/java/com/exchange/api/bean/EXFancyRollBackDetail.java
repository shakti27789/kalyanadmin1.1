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
@Table(name = "t_fancy_rollback_detail")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXFancyRollBackDetail {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;


	@Column(name = "fancyid")
	String fancyId;
	

	@Column(name = "status")
	String status;
	

	@Column(name = "donby")
	String doneBy;
	
	@Column(name = "createdon")
	Date createdon;
	
}
