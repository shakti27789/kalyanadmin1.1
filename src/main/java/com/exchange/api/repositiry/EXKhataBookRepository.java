package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.EXKhataBook;

public interface EXKhataBookRepository extends JpaRepository<EXKhataBook, String> {

	public EXKhataBook findByChildIdAndParentId(String childId,String parentId);
	
}
