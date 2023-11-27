package com.job.cronJob.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.job.cronJob.entity.Insurance;


public class ExcelItemProcessor implements ItemProcessor<Insurance, Insurance> {

	@Override
	public Insurance process(Insurance item) throws Exception {
		
		return item;
	}
//	 public ExcelItemProcessor() {
//		super();
//		
//	}
//
//	private static final int MAX_RECORDS = 20;
//	    private int count = 0;
//	    private List<Insurance> records;
//
//	    public ExcelItemProcessor(List<Insurance> records) {
//	        this.records = records;
//	    }
//
//	    @Override
//	    public Insurance process(Insurance item) throws Exception {
//	        if (count < MAX_RECORDS) {
//	            count++;
//	            System.out.println("Processing Record: " + item.toString());
//	            return item;
//	        } else {
//	           
//	            count = 0;
//
//	          
//	            List<Insurance> nextRecords = getNextRecords();
//
//	            
//	            if (nextRecords != null && !nextRecords.isEmpty()) {
//	                System.out.println("Fetching next batch of records.");
//	                return nextRecords.get(0); 
//	            } else {
//	                
//	                return null;
//	            }
//	        }
//	    }
//
//	    private List<Insurance> getNextRecords() {
//	        int startIndex = count; //0
//	        int endIndex = Math.min(startIndex + MAX_RECORDS, records.size());//20,500//20
//	        if (startIndex < endIndex) {
//	            return records.subList(startIndex, endIndex);
//	        } else {
//	            return Collections.emptyList();
//	        }
//	    }
}
