package com.job.cronJob.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import antlr.StringUtils;

@RestController
public class MainController {

	
//	@PostMapping("/uploadFile")
//    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
//       
//        
//        return ResponseEntity.ok("File uploaded successfully");
//    }
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job myJob;
	private boolean fileUploaded = false;

	  

	@PostMapping("/uploadFile")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {
       
		//no file uploaded
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }

        try {
            //filename //InsuranceData.xlsx
            String filename = file.getOriginalFilename();
            System.out.println(filename);

           //setting the path src\main\resources
            Path resourceDirectory = Paths.get("src", "main", "resources");
            System.out.println(resourceDirectory);
            //uploadpath src\main\resources\files
            Path uploadPath = resourceDirectory.resolve("files");
           //System.out.println(uploadPath);
            
            if (!Files.exists(uploadPath.resolve(filename))) {
                Files.createDirectories(uploadPath);
            }
            

            // Copy  file to destination src\main\resources\files\InsuranceData.xlsx
            Path targetLocation = uploadPath.resolve(filename);
            System.out.println(targetLocation);
            
            //the line Files.copy(file.getInputStream(), targetLocation) is copying the content of the uploaded file
            //(file) to a specific location (targetLocation). 
            Files.copy(file.getInputStream(), targetLocation);
           
            
      
            return ResponseEntity.ok("File uploaded successfully");
        } catch (NoSuchFileException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload the file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	 @Value("${upload.directory}")
	    private String uploadDirectory;

	   
	    @Scheduled(cron = "0/20 * * * * ?") 
	    public void runJob() throws Exception {
	        try {
	            
	            Path uploadPath = Paths.get(uploadDirectory);
	            if (!Files.exists(uploadPath)) {
	                Files.createDirectories(uploadPath);
	            }
	            
	            if (Files.list(uploadPath).findAny().isPresent()) {
	              

	                JobParameters jobParameters =
	                        new JobParametersBuilder()
	                                .addLong("time", System.currentTimeMillis())
	                                .toJobParameters();

	                org.springframework.batch.core.JobExecution execution = jobLauncher.run(myJob, jobParameters);
	                System.out.println("Exit Status : " + execution.getStatus());
	            } else {
	                
	                System.out.println("No file found in the specified directory.");
	            }

	        } catch (Exception e) {
	           System.out.println("no file uploaded");
	        }
	    }
  
   
}
