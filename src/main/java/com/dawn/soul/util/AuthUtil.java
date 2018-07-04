package com.dawn.soul.util;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dawn.soul.domain.AdminVO;
import com.dawn.soul.domain.RoleVO;
import com.dawn.soul.service.AdminService;
import com.dawn.soul.service.RoleService;

@Component
public class AuthUtil {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private RoleService roleService;

	public boolean hasRole(String adminId, String role) throws SQLException {
		AdminVO admin = adminService.getAdminById(adminId);
		if(admin.getAdminRootyn().equals("Y")) return true;
		
		List<RoleVO> roleList = roleService.getAuthListById(adminId);
		for(RoleVO r : roleList) {
			RoleVO r2 = roleService.getRoleByNo(r.getRoleNo());
			if(r2.getRoleName().equals(role)) return true;
		}
		return false;
	}
}
