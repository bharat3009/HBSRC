package com.ga.persistance.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ga.persistance.entity.Area;
import com.ga.persistance.entity.CommentHistory;
import com.ga.persistance.mapper.IAreaMapper;

@Repository
public class AreaMapperImpl implements IAreaMapper {

	@Autowired
    SessionFactory sessionFactory;
	
	@Override
	public Area getAreaById(String areaId) {
		// TODO Auto-generated method stub
		
		return null;
	}

	public List<Area> getAreaByName(String areaName){
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
		List<Area> areaList = new ArrayList<Area>();
		String hql = "FROM Area where AreaName is '" + areaName + "' or zip ='" + areaName + "'";
        Query query = session.createQuery(hql);
        areaList = query.list();
        System.out.println(areaList);
        return areaList;
		
	}

	public List<Area> getAllAreas() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Area> areaList = new ArrayList<Area>();
		String hql = "FROM Area";
		Query query = session.createQuery(hql);
		areaList = query.list();
		System.out.println(areaList);
		return areaList;
	}

	public String areaExists(String[] areaParam) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String hql;
		if(areaParam.length == 4){
			hql = "FROM Area where regionName = '" + areaParam[0] + "' and areaName = '" + areaParam[1] + "' and state = '" + areaParam[2] + "' and country = '" + areaParam[3] + "'";
		}else{
			hql = "FROM Area where areaName = '" + areaParam[0] + "' and state = '" + areaParam[1] + "' and country = '" + areaParam[2] + "'";
		}
		Query query = session.createQuery(hql);
		List<Area> resultArea = query.list();
		if(resultArea.size() > 0){
			return resultArea.get(0).getAreaId();
		}else
		return null;
	}

	public boolean createAreaId(String[] areaParam) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Area area = new Area();
		
		//String hql;
		if(areaParam.length == 4){
			area.setRegionName(areaParam[0]);
			area.setAreaName(areaParam[1]);
			area.setState(areaParam[2]);
			area.setCountry(areaParam[3]);
			session.save(area);
			
		}else{
			area.setAreaName(areaParam[0]);
			area.setState(areaParam[1]);
			area.setCountry(areaParam[2]);
			session.save(area);
		}
		session.getTransaction().commit();
        session.close();
        return true;
	}
}
