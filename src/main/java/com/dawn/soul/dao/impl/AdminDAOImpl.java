package com.dawn.soul.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dawn.soul.dao.AdminDAO;
import com.dawn.soul.domain.AdminVO;

@Repository
public class AdminDAOImpl implements AdminDAO {

	private static final String NAMESPACE = "Admin-Mapper";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<AdminVO> selectAdminList() throws SQLException {
		return sqlSession.selectList(NAMESPACE + ".selectAdminList");
	}

	@Override
	public AdminVO selectAdminById(String adminId) throws SQLException {
		return sqlSession.selectOne(NAMESPACE + ".selectAdminById", adminId);
	}

	@Override
	public void insertAdmin(AdminVO admin) throws SQLException {
		sqlSession.insert(NAMESPACE + ".insertAdmin", admin);
	}

	@Override
	public void updateAdmin(AdminVO admin) throws SQLException {
		sqlSession.update(NAMESPACE + ".updateAdmin", admin);
	}

	@Override
	public void deleteAdmin(String adminId) throws SQLException {
		sqlSession.delete(NAMESPACE + ".deleteAdmin", adminId);
	}

	@Override
	public void updateAdminUseyn(AdminVO admin) throws SQLException {
		sqlSession.update(NAMESPACE + ".updateAdminUseyn", admin);
	}

	@Override
	public List<AdminVO> selectAdminWaitList() throws SQLException {
		return sqlSession.selectList(NAMESPACE + ".selectAdminWaitList");
	}

	@Override
	public void deleteAdminAll() throws SQLException {
		sqlSession.delete(NAMESPACE + ".deleteAdminAll");
	}

}
