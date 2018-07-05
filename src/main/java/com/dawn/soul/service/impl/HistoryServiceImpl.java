package com.dawn.soul.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawn.soul.dao.HistoryDAO;
import com.dawn.soul.domain.HistoryVO;
import com.dawn.soul.service.HistoryService;

@Service
public class HistoryServiceImpl implements HistoryService {

	@Autowired
	private HistoryDAO historyDAO;
	
	@Override
	public List<HistoryVO> getHistoryList() throws SQLException {
		return historyDAO.selectHistoryList();
	}

	@Override
	public HistoryVO getHistoryById(int historyNo) throws SQLException {
		return historyDAO.selectHistoryByNo(historyNo);
	}

	@Override
	public void insertHistory(HistoryVO history) throws SQLException {
		historyDAO.insertHistory(history);
	}

	@Override
	public void modifyHistory(HistoryVO history) throws SQLException {
		historyDAO.updateHistory(history);
	}

	@Override
	public void removeHistory(int historyNo) throws SQLException {
		historyDAO.deleteHistory(historyNo);
	}

}
