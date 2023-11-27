package com.job.cronJob.entity;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class EmailScheduler {
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job notificationJob;
//	@Scheduled(cron = "0 0 */6 * * *") 6 hours

  //  @Scheduled(cron = "0/20 * * * * ?")//20 sec
	//fetching batch
//EMAIL
	@Scheduled(cron = "0 0 */6 * * *") //6 hours
    public void runJob() throws Exception {
    	 try {
    			JobParameters jobParameters = 
    			  new JobParametersBuilder()
    			  .addLong("time",System.currentTimeMillis()).toJobParameters();
    				
    			org.springframework.batch.core.JobExecution execution = jobLauncher.run(notificationJob, jobParameters);
    			System.out.println("Exit Status : " + execution.getStatus());
    				
    		  } catch (Exception e) {
    			e.printStackTrace();
    		  }

    }
}
