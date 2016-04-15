package com.ga.repository;

import java.util.List;

import com.ga.domain.model.AreaDTO;

public interface IAreaService {
	
	List<String> getAreaByName(String areaName);

	List<AreaDTO> getAllAreas();

	int getAreaId(String area);

}
