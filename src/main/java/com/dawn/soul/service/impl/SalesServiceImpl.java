package com.dawn.soul.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawn.soul.dao.HistoryDAO;
import com.dawn.soul.dao.SalesDAO;
import com.dawn.soul.dao.SalesDetailDAO;
import com.dawn.soul.domain.AdminVO;
import com.dawn.soul.domain.HistoryVO;
import com.dawn.soul.domain.SalesDetailVO;
import com.dawn.soul.domain.SalesVO;
import com.dawn.soul.service.SalesService;

@Service
public class SalesServiceImpl implements SalesService {

	@Autowired
	private SalesDAO salesDAO;
	
	@Autowired
	private SalesDetailDAO salesDetailDAO;
	
	@Autowired
	private HistoryDAO historyDAO;
	
	@Override
	public List<SalesVO> getSalesList() throws SQLException {
		return salesDAO.selectSalesList();
	}

	@Override
	public List<SalesDetailVO> getSalesDetList(int salesNo) throws SQLException {
		return salesDetailDAO.selectSalesDetailListByNo(salesNo);
	}
	
	@Override
	public SalesVO getSalesByNo(int salesNo) throws SQLException {
		return salesDAO.selectSalesByNo(salesNo);
	}

	@Override
	public void insertSales(AdminVO admin, SalesVO sales, List<SalesDetailVO> salesList) throws SQLException {
		salesDAO.insertSales(sales);
		int salesNo = salesDAO.selectSalesMaxNo();
		String items = "[";
		int idx = 0;
		for(SalesDetailVO salesDetail : salesList) {
			salesDetail.setSalesNo(salesNo);
			salesDetailDAO.insertSalesDetail(salesDetail);
			items += salesDetail.getItemName();
			items += (idx++ == salesList.size()-1) ? "" : ", "; 
		}
		items += "]";
		
		String text = admin.getAdminName() + " (" + admin.getAdminId() + ") 님이 " + sales.getSalesDate() + " 일자에 " + sales.getCompanyName() + " 에게 " + items + " 등을 영업 등록 했습니다.";
		HistoryVO history = new HistoryVO();
		history.setHistoryText(text);
		historyDAO.insertHistory(history);
	}

	@Override
	public void modifySales(AdminVO admin, SalesVO sales, List<SalesDetailVO> salesList) throws SQLException {
		salesDAO.updateSales(sales);
		String items = "[";
		int idx = 0;
		salesDetailDAO.deleteSalesDetail(sales.getSalesNo());
		for(SalesDetailVO salesDetail : salesList) {
			salesDetail.setSalesNo(sales.getSalesNo());
			salesDetailDAO.insertSalesDetail(salesDetail);
			items += salesDetail.getItemName();
			items += (idx++ == salesList.size()-1) ? "" : ", "; 
		}
		items += "]";
		
		String text = admin.getAdminName() + " (" + admin.getAdminId() + ") 님이 " + sales.getSalesDate() + " 일자에 " + sales.getCompanyName() + " 의 영업 정보를 " + items + " 로 변경 했습니다.";
		HistoryVO history = new HistoryVO();
		history.setHistoryText(text);
		historyDAO.insertHistory(history);
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
		return salesDAO.selectSearchSales(params);
	}

	@Override
	public List<SalesVO> getSearchSales(String type, String date, String areaList, String depyn)
			throws SQLException {
		String[] list = areaList != null ? areaList.split(", ") : null;
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("date", date);
		params.put("areaList", list);
		params.put("depyn", depyn);
		return salesDAO.selectSearchSales(params);
	}
	
	@Override
	public List<SalesVO> getSalesListByDate(String salesDate) throws SQLException {
		return salesDAO.selectSalesListByDate(salesDate);
	}

	@Override
	public void removeSalesDetail(int salesNo) throws SQLException {
		salesDetailDAO.deleteSalesDetail(salesNo);
	}

	@Override
	public void modifyDepyn(AdminVO admin, SalesVO sales, String page) throws SQLException {
		SalesVO s = salesDAO.selectSalesByNo(sales.getSalesNo());
		
		List<SalesDetailVO> salesDetList = salesDetailDAO.selectSalesDetailListByNo(sales.getSalesNo());
		int total = 0;
		for(SalesDetailVO salesDet : salesDetList) {
			total += salesDet.getItemPrice() * salesDet.getAmount();
		}
		String text = "";
		if(!"real".equals(page))
			text = admin.getAdminName() + " (" + admin.getAdminId() + ") 님이 " + s.getLeader() + " 팀장의 " + total + "원에 대한 입금처리를 했습니다. (미수금 " + sales.getNoneDep() + "원)";
		else {
			text = admin.getAdminName() + " (" + admin.getAdminId() + ") 님이 " + s.getLeader() + " 팀장의 " + s.getNoneDep() + "원에 대한 미수금처리를 했습니다. (남은 미수금 " + (s.getNoneDep() - sales.getNoneDep()) + "원)";
			sales.setNoneDep(s.getNoneDep() - sales.getNoneDep());
		}
		HistoryVO history = new HistoryVO();
		history.setHistoryText(text);
		historyDAO.insertHistory(history);
		
		salesDAO.updateDepyn(sales);
	}

	@Override
	public List<SalesVO> getRealSalesList() throws SQLException {
		return salesDAO.selectRealSalesList();
	}
	
}