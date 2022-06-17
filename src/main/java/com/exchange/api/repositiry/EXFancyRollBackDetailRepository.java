package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXFancyRollBackDetail;

@Repository
public interface EXFancyRollBackDetailRepository extends JpaRepository<EXFancyRollBackDetail, Integer> {

	ArrayList<EXFancyRollBackDetail> findByfancyId(String fancyId);
}
