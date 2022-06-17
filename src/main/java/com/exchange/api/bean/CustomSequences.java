package com.exchange.api.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Entity
@Table(name = "t_customsequences")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomSequences {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	private String marketid;

	private Integer seq;

}