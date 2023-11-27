package com.job.cronJob.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;

import com.job.cronJob.entity.Insurance;


public class ExcelDataRowMapper implements RowMapper<Insurance> {

	   @Override
	    public com.job.cronJob.entity.Insurance mapRow(RowSet rs) throws Exception {
	        com.job.cronJob.entity.Insurance insurance = new Insurance();
	        
	        //returning the row in string array.
	        String[] rowDataArray = rs.getCurrentRow();
	        
	       // System.out.println(rowDataArray);
	        
	        //converting the string array into readable represntation.
	        List<String> rowData = Arrays.asList(rowDataArray);
       //  System.out.println(rowData); //returns all the rows data=[101126, 31-Dec-21, Urban, WI, Midwest, 1,776,800, Personal, Nehemiah Broughton , FB ,  5-11 , 257, 28, 3, The Citadel, Nehemiah, Broughton , preeti.s@mypcot.com, 111]
	       
	        insurance.setPolicy(Integer.parseInt(rowData.get(0)));
	        insurance.setCategory(rowData.get(6));
	        insurance.setEmail(rowData.get(16));
	        insurance.setName(rowData.get(7));
	        
	        //Insurance [Policy=101117, Category=Personal, NAME=Bertrand Berry , Email=preeti.s@mypcot.com]
	        //System.out.println(insurance);
	        return insurance;
	    }
	   

}

