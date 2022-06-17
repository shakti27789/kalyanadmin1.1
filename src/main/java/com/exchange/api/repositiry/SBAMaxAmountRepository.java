package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.SBAMaxAmount;
import java.lang.Integer;
import java.util.List;
import java.util.Optional;

public interface SBAMaxAmountRepository extends JpaRepository<SBAMaxAmount, Integer> {
	
	SBAMaxAmount findByEventidAndUserid(Integer eventid, Integer userId);
	
	void deleteByEventidAndUserid(Integer eventid, Integer userId);

}
