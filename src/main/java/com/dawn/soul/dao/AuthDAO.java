package com.dawn.soul.dao;

import java.sql.SQLException;
import java.util.List;

import com.dawn.soul.domain.RoleVO;

public interface AuthDAO {

	List<RoleVO> selectAuthList() throws SQLException;

	List<RoleVO> selectAuthListById(String adminId) throws SQLException;
	
	RoleVO selectAuthByNo(int authNo) throws SQLException;

	void insertAuth(RoleVO auth) throws SQLException;

	void updateAuth(RoleVO auth) throws SQLException;

	void deleteAuth(int authNo) throws SQLException;
	
	void deleteAuthById(String adminId) throws SQLException;
}
