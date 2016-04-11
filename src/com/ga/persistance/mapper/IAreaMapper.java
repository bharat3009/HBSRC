package com.ga.persistance.mapper;

import java.util.List;

import com.ga.persistance.entity.Area;

public interface IAreaMapper {
	
	Area getAreaById(String areaId);
	
	public List<Area> getAllAreas();

}
