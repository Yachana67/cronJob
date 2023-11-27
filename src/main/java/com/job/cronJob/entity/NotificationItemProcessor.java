package com.job.cronJob.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import com.job.cronJob.entity.EmailSenderService;
import com.job.cronJob.entity.Insurance;
import com.job.cronJob.entity.Notification_log;
import com.job.cronJob.repo.NotificationLOGRepo;

@Component
public class NotificationItemProcessor implements ItemProcessor<Insurance, Notification_log> {
//	@Autowired
//    private EmailSenderService emailSenderService;
//
//    @Override
//    public Notification_log process(Insurance insurance) {
//        String to = insurance.getEmail();
//        String subject = "Email Batch";
//        String bodyContent = "Batch completed";
//        String from = "https://github.com/Yachana67/springBatch";
//
//        try {
//            emailSenderService.sendEmail(to, subject, bodyContent);
//
//            Notification_log notificationLog = new Notification_log();
//            notificationLog.setRecipient(to);
//            notificationLog.setSender("yachana1412@gmail.com");
//            notificationLog.setSubject(subject);
//            notificationLog.setBody_content(bodyContent);
//            notificationLog.setStatus("success");
//
//            return notificationLog;
//        } catch (MailException e) {
//            // Log failure
//            Notification_log  notificationLog = new Notification_log();
//            notificationLog.setRecipient(to);
//            notificationLog.setSender("yachana1412@gmail.com");
//            notificationLog.setSubject(subject);
//            notificationLog.setBody_content(bodyContent);
//            notificationLog.setStatus("fail");
//
//           
//            e.printStackTrace(); 
//
//            return notificationLog;
//        }
//    }
	
//	 @Autowired
//	    private EmailSenderService emailSenderService;
//
//	    
//	    private Set<String> sentEmails = new HashSet();
//
//	    @Override
//	    public Notification_log process(Insurance insurance) {
//	        String to = insurance.getEmail();
//
//	        
//	        if (sentEmails.contains(to)) {
//	            
//	            System.out.println("Email already sent to: " + to);
//
//	            
//
//	            return null;
//	        }
//
//	        String subject = "Email Batch";
//	        String bodyContent = "Batch completed";
//	        String from = "https://github.com/Yachana67/springBatch";
//
//	        try {
//	            emailSenderService.sendEmail(to, subject, bodyContent);
//
//	           
//	            sentEmails.add(to);
//
//	            Notification_log notificationLog = new Notification_log();
//	            notificationLog.setRecipient(to);
//	            notificationLog.setSender("yachana1412@gmail.com");
//	            notificationLog.setSubject(subject);
//	            notificationLog.setBody_content(bodyContent);
//	            notificationLog.setStatus("success");
//
//	            return notificationLog;
//	        } catch (MailException e) {
//	            
//	            Notification_log notificationLog = new Notification_log();
//	            notificationLog.setRecipient(to);
//	            notificationLog.setSender("yachana1412@gmail.com");
//	            notificationLog.setSubject(subject);
//	            notificationLog.setBody_content(bodyContent);
//	            notificationLog.setStatus("fail");
//
//	            e.printStackTrace();
//
//	            return notificationLog;
//	        }
//	    }
	
	@Autowired
    private EmailSenderService emailSenderService;

   
    @Autowired
    private NotificationLOGRepo notificationLogRepository;

    private Set<String> sentEmails = new HashSet<>();

    @Override
    public Notification_log process(Insurance insurance) {
        String to = insurance.getEmail();

       
        if (sentEmails.contains(to)) {
            System.out.println("Email already sent to: " + to);
            return null;
        }

   
        if (hasEmailBeenSent(to)) {
            System.out.println("Email already sent to: " + to + " (from database)");
            return null;
        }

        String subject = "Email Batch";
        String bodyContent = "Batch completed";

        try {
            emailSenderService.sendEmail(to, subject, bodyContent);

           
            sentEmails.add(to);

            Notification_log notificationLog = new Notification_log();
            notificationLog.setRecipient(to);
            notificationLog.setSender("yachana1412@gmail.com");
            notificationLog.setSubject(subject);
            notificationLog.setBody_content(bodyContent);
            notificationLog.setStatus("success");

           
            notificationLogRepository.save(notificationLog);

            return notificationLog;
        } catch (MailException e) {
            Notification_log notificationLog = new Notification_log();
            notificationLog.setRecipient(to);
            notificationLog.setSender("yachana1412@gmail.com");
            notificationLog.setSubject(subject);
            notificationLog.setBody_content(bodyContent);
            notificationLog.setStatus("fail");

            e.printStackTrace();

            return notificationLog;
        }
    }

    private boolean hasEmailBeenSent(String email) {
        
        return notificationLogRepository.existsByRecipient(email);
    }
}
