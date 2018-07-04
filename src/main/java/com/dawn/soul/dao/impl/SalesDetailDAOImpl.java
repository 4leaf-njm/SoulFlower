package com.dawn.soul.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dawn.soul.dao.SalesDetailDAO;
import com.dawn.soul.domain.SalesDetailVO;

@Repository
public class SalesDetailDAOImpl implements SalesDetailDAO {

	private static final String NAMESPACE = "SalesDetail-Mapper";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<SalesDetailVO> selectSalesDetailList() throws SQLException {
		return sqlSession.selectList(NAMESPACE + ".selectSalesDetList");
	}
	
	@Override
	public List<SalesDetailVO> selectSalesDetailListByNo(int salesNo) throws SQLException {
		return sqlSession.selectList(NAMESPACE + ".selectSalesDetListByNo", salesNo);
	}
	
	@Override
	public SalesDetailVO selectSalesDetailByNo(int salesDetailNo) throws SQLException {
		return sqlSession.selectOne(NAMESPACE + ".selectSalesDetByNo", salesDetailNo);
	}

	@Override
	public void insertSalesDetail(SalesDetailVO salesDetail) throws SQLException {
		sqlSession.insert(NAMESPACE + ".insertSalesDet", salesDetail);
	}

	@Override
	public void updateSalesDetail(SalesDetailVO salesDetail) throws SQLException {
		sqlSession.update(NAMESPACE + ".updateSalesDet", salesDetail);
	}

	@Override
	public void deleteSalesDetail(int salesNo) throws SQLException {
		sqlSession.delete(NAMESPACE + ".deleteSalesDet", salesNo);
	}

}
