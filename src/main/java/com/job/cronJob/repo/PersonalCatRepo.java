package com.job.cronJob.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.cronJob.entity.Insurance;
import com.job.cronJob.entity.PersonalCat;

public interface PersonalCatRepo extends JpaRepository<PersonalCat, Integer> {

	void save(Insurance item);

}
