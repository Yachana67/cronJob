package com.job.cronJob.entity;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.job.cronJob.repo.ExcelLogRepo;
@Service
public class ExcelService {
	@Autowired
    private ExcelLogRepo excelLogRepo;

    @Transactional
    public void logExcelEntry(int noOfRowsRetrieved, String status) {
        Excel_Log excelLog = new Excel_Log();
        excelLog.setTimestamp(LocalDateTime.now());
        excelLog.setNoOfRowsRetreived(noOfRowsRetrieved);
        excelLog.setStatus(status);
        excelLogRepo.save(excelLog);
        System.out.println("Entry logged successfully!");
    }
}
