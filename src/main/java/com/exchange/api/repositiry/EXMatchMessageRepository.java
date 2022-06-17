package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXMatchMessage;

@Repository
public interface EXMatchMessageRepository extends JpaRepository<EXMatchMessage, Integer> {

  public ArrayList<EXMatchMessage>  findByMatchid(Integer matchid);	
  
  public ArrayList<EXMatchMessage>  findByMatchidAndIsactive(Integer matchid,Boolean isactive);
 
}
