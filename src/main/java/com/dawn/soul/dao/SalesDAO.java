package com.dawn.soul.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.dawn.soul.domain.SalesVO;

public interface SalesDAO {

	List<SalesVO> selectSalesList() throws SQLException;

	SalesVO selectSalesByNo(int salesNo) throws SQLException;

	void insertSales(SalesVO sales) throws SQLException;

	void updateSales(SalesVO sales) throws SQLException;

	void deleteSales(int salesNo) throws SQLException;
	
	int selectSalesMaxNo() throws SQLException;
	
	List<SalesVO> selectSearchSales(HashMap<String, Object> params) throws SQLException;
	
	List<SalesVO> selectSalesListByDate(String salesDate) throws SQLException;
	
	List<SalesVO> selectRealSalesList() throws SQLException;
	
	void updateDepyn(SalesVO sales) throws SQLException;
	
}
