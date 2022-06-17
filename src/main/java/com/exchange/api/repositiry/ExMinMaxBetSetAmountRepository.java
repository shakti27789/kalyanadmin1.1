package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exchange.api.bean.ExMinMaxBetSetAmount;

public interface ExMinMaxBetSetAmountRepository extends JpaRepository<ExMinMaxBetSetAmount, String> {

	@Query(nativeQuery = true, value = "SELECT * FROM t_minmaxbetamount minmax WHERE minmax.type = :type ORDER BY minmax.amount ")
	public ArrayList<ExMinMaxBetSetAmount> findByType(@Param("type") String type);
	
	public ExMinMaxBetSetAmount findByAmountAndType(Integer amount, String type);
	
	public long deleteByid(Integer id);
	
	public ExMinMaxBetSetAmount findById(Integer id);
	
	public ArrayList<ExMinMaxBetSetAmount> findByIsActive(Boolean isActive);
	
}
