package com.dawn.soul.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawn.soul.dao.AdminDAO;
import com.dawn.soul.dao.HistoryDAO;
import com.dawn.soul.domain.AdminVO;
import com.dawn.soul.domain.HistoryVO;
import com.dawn.soul.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDAO adminDAO;
	
	@Autowired
	private HistoryDAO historyDAO;
	
	@Override
	public List<AdminVO> getAdminList() throws SQLException {
		return adminDAO.selectAdminList();
	}

	@Override
	public AdminVO getAdminById(String adminId) throws SQLException {
		return adminDAO.selectAdminById(adminId);
	}

	@Override
	public void insertAdmin(AdminVO admin) throws SQLException {
		adminDAO.insertAdmin(admin);
	}

	@Override
	public void modifyAdmin(AdminVO admin) throws SQLException {
		adminDAO.updateAdmin(admin);
	}

	@Override
	public void removeAdmin(String adminId) throws SQLException {
		adminDAO.deleteAdmin(adminId);
	}

	@Override
	public int getLoginResult(AdminVO admin) throws SQLException {
		AdminVO loginUser = adminDAO.selectAdminById(admin.getAdminId());
		if(loginUser == null) 
			return -1; // 아이디 존재 x
		else {
			if(loginUser.getAdminPwd().equals(admin.getAdminPwd())) {
				if(loginUser.getAdminUseyn().equals("R"))
					return 2; // 관리자 승인 필요
				else {
					HistoryVO history = new HistoryVO();
					String text = loginUser.getAdminName() + " (" + loginUser.getAdminId() + ") 님이 로그인 하였습니다.";
					history.setHistoryText(text);
					historyDAO.insertHistory(history);
					return 1; // 로그인 성공
				}
			} else
				return 0; // 비밀번호 일치 x
		}
	}

	@Override
	public List<AdminVO> getAdminWaitList() throws SQLException {
		return adminDAO.selectAdminWaitList();
	}

	@Override
	public void modifyAdminUseyn(String adminId, String useyn) throws SQLException {
		AdminVO admin = new AdminVO();
		admin.setAdminId(adminId);
		admin.setAdminUseyn(useyn);
		adminDAO.updateAdminUseyn(admin);
		adminDAO.deleteAdminAll();
	}

}
