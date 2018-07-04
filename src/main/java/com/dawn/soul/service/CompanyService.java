package com.dawn.soul.service;

import java.sql.SQLException;
import java.util.List;

import com.dawn.soul.domain.CompanyVO;

public interface CompanyService {

	List<CompanyVO> getCompanyList() throws SQLException;
	
	CompanyVO getCompanyById(int companyNo) throws SQLException;
	
	void insertCompany(CompanyVO company) throws SQLException;
	
	void modifyCompany(CompanyVO company) throws SQLException;
	
	void removeCompany(int companyNo) throws SQLException;
	
	List<CompanyVO> getSearchCompany(String searchToken) throws SQLException;
}
