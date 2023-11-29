package com.job.cronJob.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MyController {

	@PostMapping("/uploadFiles")
	public String uploadFile(@RequestParam("file") MultipartFile file)
	{
		return "file uploaded";
		
	}
}
