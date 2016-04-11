package com.ga.persistance.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;



@Entity
@Table(name = "Area")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Area.findAll", query = "SELECT c FROM Area c"),
        @NamedQuery(name = "Area.findByAreaId", query = "SELECT c FROM Area c WHERE c.areaId = :areaId"),
        @NamedQuery(name = "Area.findByAreaName", query = "SELECT c FROM Area c WHERE c.areaName = :areaName"),
        @NamedQuery(name = "Area.findByZip", query = "SELECT c FROM Area c WHERE c.zip = :zip"),
        @NamedQuery(name = "Area.findByState", query = "SELECT c FROM Area c WHERE c.state = :state"),
        @NamedQuery(name = "Area.findByCountry", query = "SELECT c from Area c where c.country = :country")})
public class Area implements Serializable {
	
	 private static final long serialVersionUID = 1L;
	    @Id
	    @Basic(optional = false)
	    @Column(name = "AreaId")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Expose
	    private String areaId;
	    
	    @Column(name = "AreaName")
	    @Expose
	    private String areaName;
	    
	    @Column(name = "country")
	    @Expose
	    private String country;
	    
	    @Column(name = "zip")
	    @Expose
	    private String zip;
	    
	    @Column(name = "state")
	    @Expose
	    private String state;
	    
	    @Column(name = "finalArea")
	    @Expose
	    private String finalArea;
	    
	    @Column(name = "region_name")
	    @Expose
	    private String regionName;
	    
	    
	    
	    
	    public String getRegionName() {
			return regionName;
		}

		public void setRegionName(String regionName) {
			this.regionName = regionName;
		}

		public String getFinalArea() {
			return finalArea;
		}

		public void setFinalArea(String finalArea) {
			this.finalArea = finalArea;
		}

		public Area(){
	    	
	    }
	    
	    public Area(String areaId){
	    	this.areaId = areaId;
	    }

		public String getAreaId() {
			return areaId;
		}

		public void setAreaId(String areaId) {
			this.areaId = areaId;
		}

		public String getAreaName() {
			return areaName;
		}

		public void setAreaName(String areaName) {
			this.areaName = areaName;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
	

	    @Override
	    public boolean equals(Object object) {
	        // TODO: Warning - this method won't work in the case the id fields are not set
	        if (!(object instanceof Area)) {
	            return false;
	        }
	        Area other = (Area) object;
	        if ((this.areaId == null && other.areaId != null)
	                || (this.areaId != null && !this.areaId.equals(other.areaId))) {
	            return false;
	        }
	        return true;
	    }
	

}
