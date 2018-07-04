package com.dawn.soul.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dawn.soul.dao.RoleDAO;
import com.dawn.soul.domain.RoleVO;

@Repository
public class RoleDAOImpl implements RoleDAO {

	private static final String NAMESPACE = "Role-Mapper";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<RoleVO> selectRoleList() throws SQLException {
		return sqlSession.selectList(NAMESPACE + ".selectRoleList");
	}

	@Override
	public RoleVO selectRoleByNo(int roleNo) throws SQLException {
		return sqlSession.selectOne(NAMESPACE + ".selectRoleByNo", roleNo);
	}

	@Override
	public void insertRole(RoleVO role) throws SQLException {
		sqlSession.insert(NAMESPACE + ".insertRole", role);
	}

	@Override
	public void updateRole(RoleVO role) throws SQLException {
		sqlSession.update(NAMESPACE + ".updateRole", role);
	}

	@Override
	public void deleteRole(int roleNo) throws SQLException {
		sqlSession.delete(NAMESPACE + ".deleteRole", roleNo);
	}

}
