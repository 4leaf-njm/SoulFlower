package com.dawn.soul.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class HistoryVO {

	private String historyNo;
	private String historyText;
	private Timestamp historyDate;
	
}
