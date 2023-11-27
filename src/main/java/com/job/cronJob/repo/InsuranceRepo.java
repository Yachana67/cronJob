package com.job.cronJob.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.cronJob.entity.Insurance;

public interface InsuranceRepo extends JpaRepository<Insurance, Integer>{

}
