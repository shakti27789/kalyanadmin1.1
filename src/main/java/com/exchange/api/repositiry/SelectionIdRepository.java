package com.exchange.api.repositiry;


import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.SelectionIds;

public interface SelectionIdRepository extends JpaRepository<SelectionIds, String>{
	
	
	public SelectionIds findBySelectionid(Integer selectionid);
	
	
	public ArrayList<SelectionIds> findByMarketid(String marketid);
	
	public SelectionIds findBySelectionidAndMarketid(Integer selectionid,String marketid);
}

