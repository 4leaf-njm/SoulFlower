package com.dawn.soul.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawn.soul.dao.CompanyDAO;
import com.dawn.soul.domain.CompanyVO;
import com.dawn.soul.service.CompanyService;
import com.dawn.soul.util.KeyUtil;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDAO companyDAO;
	
	@Override
	public List<CompanyVO> getCompanyList() throws SQLException {
		return companyDAO.selectCompanyList();
	}

	@Override
	public CompanyVO getCompanyById(int companyNo) throws SQLException {
		return companyDAO.selectCompanyByNo(companyNo);
	}

	@Override
	public void insertCompany(CompanyVO company) throws SQLException {
		company.setSearchToken(KeyUtil.getKoToChar(company.getCompanyName()));
		companyDAO.insertCompany(company);
	}

	@Override
	public void modifyCompany(CompanyVO company) throws SQLException {
		company.setSearchToken(KeyUtil.getKoToChar(company.getCompanyName()));
		companyDAO.updateCompany(company);
	}

	@Override
	public void removeCompany(int companyNo) throws SQLException {
		companyDAO.deleteCompany(companyNo);
	}

	@Override
	public List<CompanyVO> getSearchCompany(String searchToken) throws SQLException {
		return companyDAO.selectSearchCompany(searchToken);
	}

}
