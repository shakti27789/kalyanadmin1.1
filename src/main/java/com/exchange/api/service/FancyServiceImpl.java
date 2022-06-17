package com.exchange.api.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exchange.api.bean.ExFancyResult;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.MatchFancy;
import com.exchange.api.repositiry.BetRepository;
import com.exchange.api.repositiry.FancyRepository;
import com.exchange.api.repositiry.FancyResultRepository;

@Service("fancyService")
public class FancyServiceImpl implements FancyService{

	@Autowired
	BetRepository betRepository;
	
	@Autowired
	FancyRepository fancyRepository;
	
	@Autowired
	FancyResultRepository fancyresultRepository;
	
	@Override
	public Boolean setBetResult(String marketid, Integer result, Integer resultid) {
		
	ArrayList<ExMatchOddsBet> betList = new ArrayList<>();
		
		ArrayList<ExMatchOddsBet> list = betRepository.findByMarketid(marketid);
		
		if(!list.isEmpty()){
			for(int i=0;i<list.size();i++){
				ExMatchOddsBet bets = list.get(i);
				bets.setResult(result);
				bets.setResultid(resultid);
				betList.add(bets);
			}
		}
		
		betRepository.saveAll(betList);
		
		MatchFancy fancy = fancyRepository.findByFancyid(marketid);
		
		fancy.setStatus("RESULT");
		
		if(fancyRepository.save(fancy) == null){
			return false;
		}
		return true;
	}

	@Override
	public Boolean rollBackResult(Integer id) {
		
		ExFancyResult exFancyResult = fancyresultRepository.findByid(id);
		MatchFancy fancy = fancyRepository.findByFancyid(exFancyResult.getFancyid());
		fancy.setStatus("OPEN");
		fancy.setIsActive(false);
		fancyRepository.save(fancy);
		return true;
	}
}
