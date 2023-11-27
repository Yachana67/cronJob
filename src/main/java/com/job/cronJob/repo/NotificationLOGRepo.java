package com.job.cronJob.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.cronJob.entity.Notification_log;

public interface NotificationLOGRepo extends JpaRepository<Notification_log, Integer> {

	boolean existsByRecipient(String email);

}
