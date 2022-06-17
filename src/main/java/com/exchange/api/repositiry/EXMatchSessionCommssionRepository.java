package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.EXMatchSessionCommssion;

public interface EXMatchSessionCommssionRepository extends JpaRepository<EXMatchSessionCommssion, Integer> {

	ArrayList<EXMatchSessionCommssion> findByTypeOrderByCommssion(String type);
	
}
