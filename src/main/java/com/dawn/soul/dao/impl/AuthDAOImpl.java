package com.dawn.soul.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dawn.soul.dao.AuthDAO;
import com.dawn.soul.domain.RoleVO;

@Repository
public class AuthDAOImpl implements AuthDAO {

	private static final String NAMESPACE = "Auth-Mapper";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<RoleVO> selectAuthList() throws SQLException {
		return sqlSession.selectList(NAMESPACE + ".selectAuthList");
	}
	
	@Override
	public List<RoleVO> selectAuthListById(String adminId) throws SQLException {
		return sqlSession.selectList(NAMESPACE + ".selectAuthListById", adminId);
	}

	@Override
	public RoleVO selectAuthByNo(int authNo) throws SQLException {
		return sqlSession.selectOne(NAMESPACE + ".selectAuthByNo", authNo);
	}

	@Override
	public void insertAuth(RoleVO auth) throws SQLException {
		sqlSession.insert(NAMESPACE + ".insertAuth", auth);
	}

	@Override
	public void updateAuth(RoleVO auth) throws SQLException {
		sqlSession.update(NAMESPACE + ".updateAuth", auth);
	}

	@Override
	public void deleteAuth(int authNo) throws SQLException {
		sqlSession.delete(NAMESPACE + ".deleteAuth", authNo);
	}

	@Override
	public void deleteAuthById(String adminId) throws SQLException {
		sqlSession.delete(NAMESPACE + ".deleteAuthById", adminId);
	}

}
