package com.exchange.api.repositiry;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.exchange.api.bean.EXMatchViewer;

public interface EXMatchViewerRepository extends MongoRepository<EXMatchViewer, String> {

	
}
