package com.job.cronJob.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import com.job.cronJob.entity.Excel_Log;

public interface ExcelLogRepo extends JpaRepository<Excel_Log, Integer> {

}
