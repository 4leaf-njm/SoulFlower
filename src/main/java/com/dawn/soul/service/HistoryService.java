package com.dawn.soul.service;

import java.sql.SQLException;
import java.util.List;

import com.dawn.soul.domain.HistoryVO;

public interface HistoryService {

	List<HistoryVO> getHistoryList() throws SQLException;
	
	HistoryVO getHistoryById(int historyNo) throws SQLException;
	
	void insertHistory(HistoryVO history) throws SQLException;
	
	void modifyHistory(HistoryVO history) throws SQLException;
	
	void removeHistory(int historyNo) throws SQLException;
	
}
