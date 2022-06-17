package com.exchange.api.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Entity
@Table(name = "t_matchfancy")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXMatchFancy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "fancyid")
	private String fancyid;

	@Column(name = "name")
	private String name;

}
