package com.dawn.soul.dao;

import java.sql.SQLException;
import java.util.List;

import com.dawn.soul.domain.SalesDetailVO;

public interface SalesDetailDAO {

	List<SalesDetailVO> selectSalesDetailList() throws SQLException;

	List<SalesDetailVO> selectSalesDetailListByNo(int salesNo) throws SQLException;
	
	SalesDetailVO selectSalesDetailByNo(int salesDetailNo) throws SQLException;

	void insertSalesDetail(SalesDetailVO salesDetail) throws SQLException;

	void updateSalesDetail(SalesDetailVO salesDetail) throws SQLException;

	void deleteSalesDetail(int salesNo) throws SQLException;
}
