package com.dawn.soul.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dawn.soul.dao.HistoryDAO;
import com.dawn.soul.domain.HistoryVO;

@Repository
public class HistoryDAOImpl implements HistoryDAO {

	private static final String NAMESPACE = "History-Mapper";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<HistoryVO> selectHistoryList() throws SQLException {
		return sqlSession.selectList(NAMESPACE + ".selectHistoryList");
	}

	@Override
	public HistoryVO selectHistoryByNo(int historyNo) throws SQLException {
		return sqlSession.selectOne(NAMESPACE + ".selectHistoryByNo", historyNo);
	}

	@Override
	public void insertHistory(HistoryVO history) throws SQLException {
		sqlSession.insert(NAMESPACE + ".insertHistory", history);
	}

	@Override
	public void updateHistory(HistoryVO history) throws SQLException {
		sqlSession.update(NAMESPACE + ".updateHistory", history);
	}

	@Override
	public void deleteHistory(int historyNo) throws SQLException {
		sqlSession.delete(NAMESPACE + ".deleteHistory", historyNo);
	}

}
