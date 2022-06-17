package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.EXMaintenance;

public interface EXMaintenanceRepository  extends JpaRepository<EXMaintenance, Integer> {

	public EXMaintenance findByid(Integer id);
}
