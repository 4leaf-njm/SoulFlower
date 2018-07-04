package com.dawn.soul.dao;

import java.sql.SQLException;
import java.util.List;

import com.dawn.soul.domain.CompanyVO;

public interface CompanyDAO {

	List<CompanyVO> selectCompanyList() throws SQLException;

	CompanyVO selectCompanyByNo(int companyNo) throws SQLException;

	void insertCompany(CompanyVO company) throws SQLException;

	void updateCompany(CompanyVO company) throws SQLException;

	void deleteCompany(int companyNo) throws SQLException;
	
	List<CompanyVO> selectSearchCompany(String searchToken) throws SQLException;
}
