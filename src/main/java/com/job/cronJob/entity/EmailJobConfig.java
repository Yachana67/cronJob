package com.job.cronJob.entity;

import javax.activation.DataSource;
import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;



import com.job.cronJob.repo.NotificationLOGRepo;

@Configuration
@EnableBatchProcessing
public class EmailJobConfig {
	@Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private NotificationItemProcessor notificationItemProcessor;

    @Autowired
    private NotificationLOGRepo notificationLogRepository;

    @Autowired
    private javax.sql.DataSource dataSource;

    @Bean
    public ItemReader<Insurance> dbItemReader() {
        JdbcCursorItemReader<Insurance> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT email FROM insurance");
        reader.setRowMapper(new BeanPropertyRowMapper<>(Insurance.class));

        return reader;
    }

    
    @Bean
    public ItemProcessor<Insurance, Notification_log> dbItemProcessor()
    {
    	return new NotificationItemProcessor();
    }
    @Bean
    public ItemWriter<Notification_log> writer() {
        return notificationLogs -> notificationLogRepository.saveAll(notificationLogs);
    }

    @Bean
    public Step notificationStep() {
        return stepBuilderFactory.get("notificationStep")
                .<Insurance, Notification_log>chunk(10)
                .reader(dbItemReader())
                .processor(dbItemProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job notificationJob() {
        return jobBuilderFactory.get("notificationJob")
                .incrementer(new RunIdIncrementer())
                .start(notificationStep())
                .build();
    }

 

    
}
