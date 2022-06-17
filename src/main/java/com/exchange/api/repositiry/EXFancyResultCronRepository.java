package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.EXFancyResultCron;

public interface EXFancyResultCronRepository extends JpaRepository<EXFancyResultCron, Integer>  {

	
	public ArrayList<EXFancyResultCron> findByresultStatus(Boolean resultstatus);
	
}
