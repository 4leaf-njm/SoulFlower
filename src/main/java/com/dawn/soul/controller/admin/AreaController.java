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
import com.dawn.soul.service.AreaService;
import com.dawn.soul.util.AuthUtil;

@Controller
@RequestMapping("/admin")
public class AreaController {

	@Autowired
	private AreaService areaService;
	
	@Autowired
	private AuthUtil authUtil;
	
	@RequestMapping(value="/area.do", method=RequestMethod.GET)
	public String area(@ModelAttribute("menu_code") String menu_code, Model model, HttpSession session, RedirectAttributes rttr) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		if(!authUtil.hasRole(loginUser.getAdminId(), "ROLE_SETTING_VIEW")) {
			rttr.addAttribute("menu_code", "01");
			rttr.addFlashAttribute("msg", "권한이 없습니다.");
			return "redirect:main.do";
		}
		List<AreaVO> areaList = areaService.getAreaList(); 
		
		model.addAttribute("areaList", areaList);
		
		return "admin/area";
	}
	
	@RequestMapping(value="/area.do", method=RequestMethod.POST)
	public String area(String menu_code, AreaVO area, RedirectAttributes rttr) throws SQLException {
		areaService.insertArea(area);
		rttr.addAttribute("menu_code", menu_code);
		return "redirect:area.do";
	}
	
	@RequestMapping(value="/removeArea.do", method=RequestMethod.GET)
	public String area(String menu_code, @RequestParam("no") int areaNo, RedirectAttributes rttr) throws SQLException {
		areaService.removeArea(areaNo);
		rttr.addAttribute("menu_code", menu_code);
		return "redirect:area.do";
	}
}
