package com.dawn.soul.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dawn.soul.dao.SalesDAO;
import com.dawn.soul.domain.SalesVO;

@Repository
public class SalesDAOImpl implements SalesDAO {

	private static final String NAMESPACE = "Sales-Mapper";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<SalesVO> selectSalesList() throws SQLException {
		return sqlSession.selectList(NAMESPACE + ".selectSalesList");
	}

	@Override
	public SalesVO selectSalesByNo(int salesNo) throws SQLException {
		return sqlSession.selectOne(NAMESPACE + ".selectSalesByNo", salesNo);
	}

	@Override
	public void insertSales(SalesVO sales) throws SQLException {
		sqlSession.insert(NAMESPACE + ".insertSales", sales);
	}

	@Override
	public void updateSales(SalesVO sales) throws SQLException {
		sqlSession.update(NAMESPACE + ".updateSales", sales);
	}

	@Override
	public void deleteSales(int salesNo) throws SQLException {
		sqlSession.delete(NAMESPACE + ".deleteSales", salesNo);
	}

	@Override
	public int selectSalesMaxNo() throws SQLException {
		return sqlSession.selectOne(NAMESPACE + ".selectSalesMaxNo");
	}

	@Override
	public List<SalesVO> selectSearchSales(HashMap<String, Object> params) throws SQLException {
		return sqlSession.selectList(NAMESPACE + ".selectSearchSales", params);
	}

}
