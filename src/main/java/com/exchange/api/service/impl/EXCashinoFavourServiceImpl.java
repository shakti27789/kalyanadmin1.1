package com.exchange.api.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exchange.api.service.EXCashinoFavourService;

@Service
public class EXCashinoFavourServiceImpl implements EXCashinoFavourService {
	
	@Autowired
	private com.exchange.api.repositiry.EXCashinoFavourRepository eXCashinoFavourRepository;

	@Transactional
	@Override
	public Integer cashinoFavour(Integer matchid, String type, Boolean favour) {
		Integer staus = eXCashinoFavourRepository.findByMatchidAndType(matchid, type, favour);
		return staus;
	}

}
