package com.dawn.soul.service;

import java.sql.SQLException;
import java.util.List;

import com.dawn.soul.domain.AdminVO;
import com.dawn.soul.domain.SalesDetailVO;
import com.dawn.soul.domain.SalesVO;

public interface SalesService {

	List<SalesVO> getSalesList() throws SQLException;
	
	List<SalesDetailVO> getSalesDetList(int salesNo) throws SQLException;
	
	SalesVO getSalesByNo(int salesNo) throws SQLException;
	
	void insertSales(AdminVO admin, SalesVO sales, List<SalesDetailVO> salesList) throws SQLException;
	
	void modifySales(AdminVO admin, SalesVO sales, List<SalesDetailVO> salesList) throws SQLException;
	
	void removeSales(int salesNo) throws SQLException;
	
	List<SalesVO> getSearchSales(String type, String date, String areaList) throws SQLException;

	List<SalesVO> getSearchSales(String type, String date, String areaList, String[] companyList) throws SQLException;
	
	List<SalesVO> getSearchSales(String type, String date, String areaList, String depyn) throws SQLException;
	
	List<SalesVO> getSalesListByDate(String salesDate) throws SQLException;
	
	void removeSalesDetail(int salesNo) throws SQLException;
	
	void modifyDepyn(AdminVO admin, SalesVO sales, String page) throws SQLException;
	
	List<SalesVO> getRealSalesList() throws SQLException;
}
