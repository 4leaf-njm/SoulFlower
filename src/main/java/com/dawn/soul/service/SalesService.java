package com.dawn.soul.service;

import java.sql.SQLException;
import java.util.List;

import com.dawn.soul.domain.SalesDetailVO;
import com.dawn.soul.domain.SalesVO;

public interface SalesService {

	List<SalesVO> getSalesList() throws SQLException;
	
	List<SalesDetailVO> getSalesDetList(int salesNo) throws SQLException;
	
	SalesVO getSalesById(int salesNo) throws SQLException;
	
	void insertSales(SalesVO sales, List<SalesDetailVO> salesList) throws SQLException;
	
	void modifySales(SalesVO sales, List<SalesDetailVO> salesList) throws SQLException;
	
	void removeSales(int salesNo) throws SQLException;
	
	List<SalesVO> getSearchSales(String type, String date, String areaList) throws SQLException;

	List<SalesVO> getSearchSales(String type, String date, String areaList, String[] companyList) throws SQLException;
}
