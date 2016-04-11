package com.ga.domain.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ga.common.JsonUtility;
import com.ga.domain.model.AreaDTO;
import com.ga.exception.ErrorCodes;
import com.ga.exception.GAException;
import com.ga.repository.IAreaService;
import com.ga.repository.impl.AreaServiceImpl;

@RestController
@RequestMapping("/area")
public class AreaController {
		
	/** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AreaController.class);

    /** The comments service. */
    @Autowired
    IAreaService areaService;
    
    @RequestMapping(value = "getarea", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> getAllComments(@RequestParam("areaName") String areaName) {
        LOGGER.info("AreaName : " + areaName);
        try {
            if (areaName.isEmpty()) {
                throw new GAException(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET);
            }

            List<String> commentsDtoList = areaService.getAreaByName(areaName);
            System.out.println(commentsDtoList);
            //return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, commentsDtoList);
            return commentsDtoList;
        } catch (GAException e) {
            e.printStackTrace();
            //return JsonUtility.getJson(ErrorCodes.GA_DATA_NOT_FOUND);
            return null;
        }
       // return commentsDtoList;
    }

    
    @RequestMapping(value = "getAllAreas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getAllAreas(){
    	try{
    		List<AreaDTO> areasDtoList = areaService.getAllAreas();
    		System.out.println(areasDtoList);
    		return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, areasDtoList);
    	}catch(Exception e){
    		e.printStackTrace();    	
    		return JsonUtility.getJson(ErrorCodes.GA_DATA_NOT_FOUND);
    	}
    }

}
