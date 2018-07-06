package com.dawn.soul.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.dawn.soul.domain.HistoryVO;

public interface HistoryDAO {

	List<HistoryVO> selectHistoryList() throws SQLException;

	HistoryVO selectHistoryByNo(int historyNo) throws SQLException;

	void insertHistory(HistoryVO history) throws SQLException;

	void updateHistory(HistoryVO history) throws SQLException;

	void deleteHistory(int historyNo) throws SQLException;
	
	List<HistoryVO> selectSearchHistory(Map<String, Object> params) throws SQLException;
}
