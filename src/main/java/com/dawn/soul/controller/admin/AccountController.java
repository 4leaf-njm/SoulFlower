package com.dawn.soul.controller.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dawn.soul.domain.AdminVO;
import com.dawn.soul.domain.HistoryVO;
import com.dawn.soul.domain.RoleVO;
import com.dawn.soul.service.AdminService;
import com.dawn.soul.service.HistoryService;
import com.dawn.soul.service.RoleService;
import com.dawn.soul.util.AuthUtil;

@Controller
@RequestMapping("/admin")
public class AccountController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private AuthUtil authUtil;
	
	@RequestMapping(value="/account.do", method=RequestMethod.GET)
	public String account(@ModelAttribute("menu_code") String menu_code, Model model, HttpSession session,
                          RedirectAttributes rttr) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		if(!authUtil.hasRole(loginUser.getAdminId(), "ROLE_ACCOUNT_VIEW")) {
			rttr.addAttribute("menu_code", "01");
			rttr.addFlashAttribute("msg", "권한이 없습니다.");
			return "redirect:main.do";
		}
		Map<String, Object> authMap = new HashMap<String, Object>();
		List<AdminVO> adminWaitList = adminService.getAdminWaitList();
		List<AdminVO> adminList = adminService.getAdminList();
		List<RoleVO> roleList = roleService.getRoleList();
		
		for(AdminVO admin : adminList) {
			if(admin.getAdminRootyn().equals("Y")) break;
			List<RoleVO> authList = roleService.getAuthListById(admin.getAdminId());
			String authStr = "";
			for(int i=0; i<authList.size(); i++) {
				RoleVO auth = authList.get(i);
				authStr += authList.size() - 1 == i ? auth.getRoleNo() : auth.getRoleNo() + "/";   
			}
			authMap.put(admin.getAdminId(), authStr);
		}
		model.addAttribute("adminList", adminList);
		model.addAttribute("adminWaitList", adminWaitList);
		model.addAttribute("roleList", roleList);
		model.addAttribute("authMap", authMap);
		return "admin/account";
	}
	
	@ResponseBody
	@RequestMapping(value="/approveAdmin.do", method=RequestMethod.POST)
	public Map<String, Object> approve(String adminId, String useyn, HttpSession session) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		AdminVO admin = adminService.getAdminById(adminId); 
		Map<String, Object> data = new HashMap<String, Object>();
		adminService.modifyAdminUseyn(adminId, useyn);
		String msg = "";
		String res = "";
		if(useyn.equals("Y")) {
			msg = "승인 되었습니다.";
			res = "승인";
		} else if (useyn.equals("N")) {
			msg = "승인 거부 되었습니다.";
			res = "거부";
		}
		String text = loginUser.getAdminName() + " (" + loginUser.getAdminId() + ") 님이 " + "계정 관리에서 " + admin.getAdminName() + " (" + admin.getAdminId() + ") " + " 님의 가입을 " + res + "했습니다.";
		HistoryVO history = new HistoryVO();
		history.setHistoryText(text);
		historyService.insertHistory(history);
		
		data.put("msg", msg);
		return data;
	}
	
	@ResponseBody
	@RequestMapping(value="/authorityAdmin.do", method=RequestMethod.POST)
	public Map<String, Object> authority(String adminId, String roles) throws SQLException {
		Map<String, Object> data = new HashMap<String, Object>();

		List<RoleVO> roleList = new ArrayList<RoleVO>();
		if(!roles.equals("")) {
			String[] rolesArr = roles.split(", ");
			for(String role : rolesArr) {
				RoleVO r = new RoleVO();
				r.setAdminId(adminId);
				r.setRoleNo(Integer.parseInt(role));
				roleList.add(r);
			}
		}
		roleService.insertAuth(adminId, roleList);
		String msg = "권한이 변경되었습니다.";
		data.put("msg", msg);
		return data;
	}
}
