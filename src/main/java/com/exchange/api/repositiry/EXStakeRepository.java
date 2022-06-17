package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXStake;

@Repository
public interface EXStakeRepository extends JpaRepository<EXStake, Integer> {

}
