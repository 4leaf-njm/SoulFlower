package com.dawn.soul.service;

import java.sql.SQLException;
import java.util.List;

import com.dawn.soul.domain.RoleVO;

public interface RoleService {

	List<RoleVO> getRoleList() throws SQLException;
	
	List<RoleVO> getAuthListById(String adminId) throws SQLException;
	
	RoleVO getRoleByNo(int roleNo) throws SQLException;
	
	void insertAuth(String adminId, List<RoleVO> roleList) throws SQLException;
	
}
