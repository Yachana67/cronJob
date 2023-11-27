package com.job.cronJob.config;

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
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
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
	@Bean
	public ItemReader<Insurance> excelItemReader(int jobOffset, int offset, int initialPageSize) {
	    PoiItemReader<Insurance> reader = new PoiItemReader<>();
	    reader.setResource(new ClassPathResource("InsuranceData.xlsx"));

	    int pageSize = initialPageSize; 

	    if (jobOffset == 0) {
	       
	        reader.setLinesToSkip(1);
	    } else {
	        
	        int linesToSkip = offset + offset * pageSize;
	        excelService.logExcelEntry(linesToSkip, "success");
	        reader.setLinesToSkip(linesToSkip);
	        
	        pageSize = pageSize + 100;
	       
	    }

	    reader.setRowMapper(excelRowMapper());
	    reader.setMaxItemCount(20);
	    

	    return reader;
	}
	
	@Transactional
	public void logExcelEntry(int noOfRowsRetrieved, String status) {
	    Excel_Log excelLog = new Excel_Log();
	    
	    excelLog.setTimestamp(LocalDateTime.now());
	    excelLog.setNoOfRowsRetreived(noOfRowsRetrieved);
	    excelLog.setStatus(status);

	
	    excelLogRepo.save(excelLog);
	    System.out.println("Entry logged successfully!");
	}
	 private RowMapper<Insurance> excelRowMapper() {
	        return new ExcelDataRowMapper();
	    }
	 
	
	 public ItemProcessor<Insurance, Insurance> excelItemProcessor()
	 {
		 
		 //List<Insurance> records=new ArrayList<Insurance>();
		return new ExcelItemProcessor();
		 
	 }


	 
	 @Bean
	 public ItemWriter<Insurance> excelItemWriter()
	 {
		return new ExcelItemWriter();
		 
	 }
	 
	 @Bean
	    public Step myStep() {
	        return stepBuilderFactory.get("myStep")
	                .<Insurance, Insurance>chunk(20) 
	                .reader(excelItemReader(0, 0, 20))
	                .processor(excelItemProcessor())
	                .writer(excelItemWriter())
	             .allowStartIfComplete(true)//if the step is completed
	                .build();
	    }

	    @Bean
	    public Job myJob() {
	        return jobBuilderFactory.get("myJobs")
	                .start(myStep())
	                .incrementer(new RunIdIncrementer())
	                .build();
	    }
	   


	    
}