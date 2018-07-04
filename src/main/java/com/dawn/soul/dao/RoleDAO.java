package com.dawn.soul.dao;

import java.sql.SQLException;
import java.util.List;

import com.dawn.soul.domain.RoleVO;

public interface RoleDAO {

	List<RoleVO> selectRoleList() throws SQLException;

	RoleVO selectRoleByNo(int roleNo) throws SQLException;

	void insertRole(RoleVO role) throws SQLException;

	void updateRole(RoleVO role) throws SQLException;

	void deleteRole(int roleNo) throws SQLException;
}
