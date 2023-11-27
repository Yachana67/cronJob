package com.job.cronJob.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Excel_Log {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private LocalDateTime  timestampp;
	public LocalDateTime getTimestamp() {
		return timestampp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestampp = timestamp;
	}
	private int noOfRowsRetreived;
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getNoOfRowsRetreived() {
		return noOfRowsRetreived;
	}
	public void setNoOfRowsRetreived(int noOfRowsRetreived) {
		this.noOfRowsRetreived = noOfRowsRetreived;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
