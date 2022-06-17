package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.CustomSequences;

public interface CustomSequencesRepository extends JpaRepository<CustomSequences,Integer> {

}
