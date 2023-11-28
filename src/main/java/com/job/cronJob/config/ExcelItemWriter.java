

package com.job.cronJob.config;


import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.job.cronJob.entity.HealthCat;
import com.job.cronJob.entity.Insurance;
import com.job.cronJob.entity.PersonalCat;
import com.job.cronJob.repo.HealthCatRepo;
import com.job.cronJob.repo.InsuranceRepo;
import com.job.cronJob.repo.PersonalCatRepo;

public class ExcelItemWriter implements ItemWriter<Insurance> {

	
	    @Autowired
	    private HealthCatRepo healthCatRepository;
	    @Autowired
	    private InsuranceRepo insuranceRepo;
	    
	    @Autowired
	    private PersonalCatRepo personalrepo; 

	    @Override
	    public void write(List<? extends Insurance> items) throws Exception {
      for (Insurance item : items) {
//	            if ("health".equalsIgnoreCase(item.getCategory())) {
//	            	HealthCat healthCat = new HealthCat();
//	                healthCat.setPolicy(item.getPolicy());
//	                healthCat.setCategory(item.getCategory());
//	                healthCat.setName(item.getName());
//	                healthCat.setEmail(item.getEmail());
//	              
//	                healthCatRepository.save(healthCat);
//	            } else if("personal".equalsIgnoreCase(item.getCategory())) {
//	            	PersonalCat personalCat = new PersonalCat();
//	                personalCat.setPolicy(item.getPolicy());
//	                personalCat.setCategory(item.getCategory());
//	                personalCat.setName(item.getName());
//	                personalCat.setEmail(item.getEmail());
//	               
//	                personalrepo.save(personalCat);
//	            }
//	        
    	   Insurance insurance=new Insurance();
    	   insurance.setCategory(item.getCategory());
    	   insurance.setEmail(item.getEmail());
    	   insurance.setName(item.getName());
    	   insurance.setPolicy(item.getPolicy());
    	   
    	   insuranceRepo.save(insurance);
       }
	    	
	    	
	    }

}
