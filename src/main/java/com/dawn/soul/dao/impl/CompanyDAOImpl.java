package com.dawn.soul.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dawn.soul.dao.CompanyDAO;
import com.dawn.soul.domain.CompanyVO;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

	private static final String NAMESPACE = "Company-Mapper";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<CompanyVO> selectCompanyList() throws SQLException {
		return sqlSession.selectList(NAMESPACE + ".selectCompanyList");
	}

	@Override
	public CompanyVO selectCompanyByNo(int companyNo) throws SQLException {
		return sqlSession.selectOne(NAMESPACE + ".selectCompanyByNo", companyNo);
	}

	@Override
	public void insertCompany(CompanyVO company) throws SQLException {
		sqlSession.insert(NAMESPACE + ".insertCompany", company);
	}

	@Override
	public void updateCompany(CompanyVO company) throws SQLException {
		sqlSession.update(NAMESPACE + ".updateCompany", company);
	}

	@Override
	public void deleteCompany(int companyNo) throws SQLException {
		sqlSession.delete(NAMESPACE + ".deleteCompany", companyNo);
	}

	@Override
	public List<CompanyVO> selectSearchCompany(String searchToken) throws SQLException {
		return sqlSession.selectList(NAMESPACE + ".selectSearchCompany", searchToken);
	}

}
