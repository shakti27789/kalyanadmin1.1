package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.exchange.api.bean.EXMatchFancy;

@Repository
public interface FancyRepositoryNew extends JpaRepository<EXMatchFancy, Integer> {

	
}
