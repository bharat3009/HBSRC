package com.ga.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ga.domain.model.AreaDTO;
import com.ga.persistance.entity.Area;
import com.ga.persistance.mapper.impl.AreaMapperImpl;
import com.ga.persistance.mapper.impl.UserMapperImpl;
import com.ga.repository.IAreaService;

@Service
@Transactional
public class AreaServiceImpl implements IAreaService{
	
	@Autowired
	AreaMapperImpl areaMapper;

	@Override
	public List<String> getAreaByName(String areaName) {
		// TODO Auto-generated method stub
		List<String> result = new ArrayList<String>();
		List<Area> areaList = areaMapper.getAreaByName(areaName);
		System.out.println(areaList);
		int index = 0;
		for(Area a : areaList){
			System.out.println(a.getAreaName());
			String x = a.getAreaName() + ", " + a.getState() + ", " + a.getCountry() + ", " + a.getZip() ;
			System.out.println(x);
			result.add(x);
		}
		System.out.println(result);
		
		return result;
	}
	
	@Override
	public List<AreaDTO> getAllAreas(){
		List<AreaDTO> resultArea = new ArrayList<AreaDTO>();
		List<Area> result = new ArrayList<Area>();
		result = areaMapper.getAllAreas();
		System.out.println(result);
		int index = 0;
		for(Area a : result){
			System.out.println(a.getAreaName());
			String x = a.getAreaName() + ", " + a.getState() + ", " + a.getCountry() + ", " + a.getZip() ;
			System.out.println(x);
			AreaDTO areadto = new AreaDTO();
			areadto.setAreaId(a.getAreaId());
			areadto.setAreaValue(x);
			resultArea.add(areadto);
			
		}
		System.out.println(resultArea);
		return resultArea;
	}
	
	@Override
	public String getAreaId(String area) {
		String[] areaParam = area.split(",");
		String areaExists = areaMapper.areaExists(areaParam);
		if(areaExists != null){
			return areaExists;
		}else{
			areaMapper.createAreaId(areaParam);
			String areaId = areaMapper.areaExists(areaParam);
			return areaId;
		}
		
	}

}
