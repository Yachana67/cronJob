package com.job.cronJob.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import antlr.StringUtils;

@RestController
public class MainController {

	
//	@PostMapping("/uploadFile")
//    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
//       
//        
//        return ResponseEntity.ok("File uploaded successfully");
//    }
	
	@PostMapping("/uploadFile")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
       
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
           System.out.println(uploadPath);
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Copy  file to destination src\main\resources\files\InsuranceData.xlsx
            Path targetLocation = uploadPath.resolve(filename);
            System.out.println(targetLocation);
            //the line Files.copy(file.getInputStream(), targetLocation) is copying the content of the uploaded file
            //(file) to a specific location (targetLocation). 
            Files.copy(file.getInputStream(), targetLocation);


            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload the file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
