package com.dawn.soul.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawn.soul.dao.AuthDAO;
import com.dawn.soul.dao.RoleDAO;
import com.dawn.soul.domain.RoleVO;
import com.dawn.soul.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private AuthDAO authDAO;

	@Autowired
	private RoleDAO roleDAO;

	@Override
	public List<RoleVO> getRoleList() throws SQLException {
		return roleDAO.selectRoleList();
	}
	
	@Override
	public List<RoleVO> getAuthListById(String adminId) throws SQLException {
		return authDAO.selectAuthListById(adminId);
	}

	@Override
	public RoleVO getRoleByNo(int roleNo) throws SQLException {
		return roleDAO.selectRoleByNo(roleNo);
	}
	
	@Override
	public void insertAuth(String adminId, List<RoleVO> roleList) throws SQLException {
		authDAO.deleteAuthById(adminId);
		for(RoleVO auth : roleList) {
			authDAO.insertAuth(auth);
		}
	}

}
