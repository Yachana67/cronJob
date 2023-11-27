package com.job.cronJob.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.cronJob.entity.HealthCat;
import com.job.cronJob.entity.Insurance;

public interface HealthCatRepo extends JpaRepository<HealthCat, Integer> {

	void save(Insurance item);

}
