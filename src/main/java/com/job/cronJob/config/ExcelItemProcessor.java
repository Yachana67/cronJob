package com.job.cronJob.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.job.cronJob.entity.Insurance;


public class ExcelItemProcessor implements ItemProcessor<Insurance, Insurance> {

	@Override
	public Insurance process(Insurance item) throws Exception {
		
		System.out.println(item);
		return item;
	}

}
