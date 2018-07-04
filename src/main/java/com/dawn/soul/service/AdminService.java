package com.dawn.soul.service;

import java.sql.SQLException;
import java.util.List;

import com.dawn.soul.domain.AdminVO;

public interface AdminService {

	List<AdminVO> getAdminList() throws SQLException;
	
	AdminVO getAdminById(String adminId) throws SQLException;
	
	void insertAdmin(AdminVO admin) throws SQLException;
	
	void modifyAdmin(AdminVO admin) throws SQLException;
	
	void removeAdmin(String adminId) throws SQLException;
	
	int getLoginResult(AdminVO admin) throws SQLException;
	
	List<AdminVO> getAdminWaitList() throws SQLException;
	
	void modifyAdminUseyn(String adminId, String useyn) throws SQLException;
}
