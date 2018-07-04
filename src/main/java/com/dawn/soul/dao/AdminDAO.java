package com.dawn.soul.dao;

import java.sql.SQLException;
import java.util.List;

import com.dawn.soul.domain.AdminVO;

public interface AdminDAO {

	List<AdminVO> selectAdminList() throws SQLException;

	AdminVO selectAdminById(String adminId) throws SQLException;

	void insertAdmin(AdminVO admin) throws SQLException;

	void updateAdmin(AdminVO admin) throws SQLException;

	void deleteAdmin(String adminId) throws SQLException;
	
	void updateAdminUseyn(AdminVO admin) throws SQLException;
	
	List<AdminVO> selectAdminWaitList() throws SQLException;
	
	void deleteAdminAll() throws SQLException;
}
