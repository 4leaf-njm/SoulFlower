package com.dawn.soul.controller.admin;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dawn.soul.domain.AdminVO;
import com.dawn.soul.domain.AreaVO;
import com.dawn.soul.domain.HistoryVO;
import com.dawn.soul.service.AreaService;
import com.dawn.soul.service.HistoryService;
import com.dawn.soul.util.AuthUtil;

@Controller
@RequestMapping("/admin")
public class AreaController {

	@Autowired
	private AreaService areaService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private AuthUtil authUtil;
	
	@RequestMapping(value="/area.do", method=RequestMethod.GET)
	public String area(@ModelAttribute("menu_code") String menu_code, Model model, HttpSession session) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		if(!authUtil.hasRole(loginUser.getAdminId(), "ROLE_SETTING_VIEW")) {
			model.addAttribute("auth", "N");
		}
		List<AreaVO> areaList = areaService.getAreaList(); 
		
		model.addAttribute("areaList", areaList);
		
		return "admin/area";
	}
	
	@RequestMapping(value="/area.do", method=RequestMethod.POST)
	public String area(String menu_code, AreaVO area, HttpSession session, RedirectAttributes rttr) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		areaService.insertArea(area);
		
		String text = loginUser.getAdminName() + " (" + loginUser.getAdminId() + ") 님이 " + "지역 관리에서 " + area.getAreaName() + " 지역을 추가했습니다.";
		HistoryVO history = new HistoryVO();
		history.setHistoryText(text);
		historyService.insertHistory(history);
		
		rttr.addAttribute("menu_code", menu_code);
		return "redirect:area.do";
	}
	
	@RequestMapping(value="/removeArea.do", method=RequestMethod.GET)
	public String area(String menu_code, @RequestParam("no") int areaNo, HttpSession session, RedirectAttributes rttr) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		AreaVO area = areaService.getAreaById(areaNo);
		areaService.removeArea(areaNo);
		
		String text = loginUser.getAdminName() + " (" + loginUser.getAdminId() + ") 님이 " + "지역 관리에서 " + area.getAreaName() + " 지역을 삭제했습니다.";
		HistoryVO history = new HistoryVO();
		history.setHistoryText(text);
		historyService.insertHistory(history);
		
		rttr.addAttribute("menu_code", menu_code);
		return "redirect:area.do";
	}
}
