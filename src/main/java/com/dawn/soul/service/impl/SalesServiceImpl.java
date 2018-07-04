package com.dawn.soul.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawn.soul.dao.SalesDAO;
import com.dawn.soul.dao.SalesDetailDAO;
import com.dawn.soul.domain.SalesDetailVO;
import com.dawn.soul.domain.SalesVO;
import com.dawn.soul.service.SalesService;

@Service
public class SalesServiceImpl implements SalesService {

	@Autowired
	private SalesDAO salesDAO;
	
	@Autowired
	private SalesDetailDAO salesDetailDAO;
	
	@Override
	public List<SalesVO> getSalesList() throws SQLException {
		return salesDAO.selectSalesList();
	}

	@Override
	public List<SalesDetailVO> getSalesDetList(int salesNo) throws SQLException {
		return salesDetailDAO.selectSalesDetailListByNo(salesNo);
	}
	
	@Override
	public SalesVO getSalesById(int salesNo) throws SQLException {
		return salesDAO.selectSalesByNo(salesNo);
	}

	@Override
	public void insertSales(SalesVO sales, List<SalesDetailVO> salesList) throws SQLException {
		salesDAO.insertSales(sales);
		int salesNo = salesDAO.selectSalesMaxNo();
		for(SalesDetailVO salesDetail : salesList) {
			salesDetail.setSalesNo(salesNo);
			salesDetailDAO.insertSalesDetail(salesDetail);
		}
	}

	@Override
	public void modifySales(SalesVO sales, List<SalesDetailVO> salesList) throws SQLException {
		salesDAO.updateSales(sales);
		for(SalesDetailVO salesDetail : salesList) {
			salesDetailDAO.updateSalesDetail(salesDetail);
		}
	}

	@Override
	public void removeSales(int salesNo) throws SQLException {
		salesDAO.deleteSales(salesNo);
		salesDetailDAO.deleteSalesDetail(salesNo);
	}

	@Override
	public List<SalesVO> getSearchSales(String type, String date, String areaList) throws SQLException {
		String[] list = areaList != null ? areaList.split(", ") : null;
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("date", date);
		params.put("areaList", list);
		return salesDAO.selectSearchSales(params);
	}

	@Override
	public List<SalesVO> getSearchSales(String type, String date, String areaList, String[] companyList)
			throws SQLException {
		String[] list = areaList != null ? areaList.split(", ") : null;
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("date", date);
		params.put("areaList", list);
		params.put("companyList", companyList);
		System.out.println("length : " + companyList.length);
		for(String company : companyList)
			System.out.println(company);
		return salesDAO.selectSearchSales(params);
	}

}
