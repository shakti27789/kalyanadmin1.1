package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exchange.api.bean.ExFancyResult;

public interface FancyResultRepository extends JpaRepository<ExFancyResult, String>{

	
	public Boolean existsByfancyid(String id);
	
	public ExFancyResult findByid(Integer id);
	
	public Boolean existsByidIn(ArrayList<String> ids);
	
	public void deleteByidIn(ArrayList<String> ids);
	
	public ArrayList<ExFancyResult> findByIsResult(Boolean isResult); 
	
	public long deleteById(Integer id);
	
	public ArrayList<ExFancyResult> findByresultStatusCron(Boolean status);
	
	public ArrayList<ExFancyResult> findByisprofitlossclear(Boolean status);

	
	public ExFancyResult findByfancyid(String fancyId);
	
	public ArrayList<ExFancyResult> findBymatchid(Integer matchId);

	
}

