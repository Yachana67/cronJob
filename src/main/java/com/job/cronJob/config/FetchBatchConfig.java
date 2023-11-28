package com.job.cronJob.config;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.batch.runtime.JobExecution;
import javax.transaction.Transactional;

import org.apache.xmlbeans.impl.common.XPath.ExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.job.cronJob.entity.ExcelService;
import com.job.cronJob.entity.Excel_Log;
import com.job.cronJob.entity.Insurance;
import com.job.cronJob.repo.ExcelLogRepo;

@Configuration
@EnableBatchProcessing
public class FetchBatchConfig {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private ExcelLogRepo excelLogRepo;
	
	@Autowired
	private ExcelService excelService;
	
	     int offset ; 
	     int initialPageSize;
	     int jobOffset;
	@Bean
	public ItemReader<Insurance> excelItemReader() {
		int linestoSkip;
		  
		if (jobOffset == 0) {
		    linestoSkip = 1;
		} else {
		    offset = 1;  
		    linestoSkip = offset + offset * initialPageSize;
		    initialPageSize = initialPageSize + 20;
		}
	    PoiItemReader<Insurance> reader = new PoiItemReader<>();
	    //reader.setResource(new ClassPathResource("InsuranceData.xlsx"));
	    try {
            Path resourceDirectory = Paths.get("src", "main", "resources");
            Path filesDirectory = resourceDirectory.resolve("files");

            
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(filesDirectory)) {
                for (Path filePath : directoryStream) {
                   
                    reader.setResource(new FileSystemResource(filePath.toString()));
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, log an error, or throw a custom exception
        }


	    reader.setLinesToSkip(linestoSkip);//21 lines skipped
	    reader.setRowMapper(excelRowMapper());
	    reader.setMaxItemCount(20);
	    

	    return reader;
	}
	   
	
	     private RowMapper<Insurance> excelRowMapper() {
		        return new ExcelDataRowMapper();
		    }
    
	//entering excel log details in the table
	@Transactional
	public void logExcelEntry(int noOfRowsRetrieved, String status) {
	    Excel_Log excelLog = new Excel_Log();
	    
	    excelLog.setTimestamp(LocalDateTime.now());
	    excelLog.setNoOfRowsRetreived(noOfRowsRetrieved);
	    excelLog.setStatus(status);

	
	    excelLogRepo.save(excelLog);
	    System.out.println("Entry logged successfully!");
	}
	 
	 
	
	 public ItemProcessor<Insurance, Insurance> excelItemProcessor()
	 {
		return new ExcelItemProcessor();
		 
	 }
	 @Bean
	 public ItemWriter<Insurance> excelItemWriter()
	 {
		return new ExcelItemWriter();
		 
	 }
	 
	 @Bean
	    public Step myStep(ItemReader<Insurance> itemReader) {
	        return stepBuilderFactory.get("myStep")
	                .<Insurance, Insurance>chunk(20)
	                .reader(itemReader)
	                .processor(excelItemProcessor())
	                .writer(excelItemWriter())
	                .allowStartIfComplete(true)
	                .build();
	    }

	    @Bean
	    public Job myJob(Step myStep) {
	        return jobBuilderFactory.get("myJobs")
	                .start(myStep)
	                .incrementer(new RunIdIncrementer())
	                .build();
	    }
	   


	    
}