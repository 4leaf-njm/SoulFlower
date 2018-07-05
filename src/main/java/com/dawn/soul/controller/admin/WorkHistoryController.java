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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dawn.soul.domain.AdminVO;
import com.dawn.soul.domain.HistoryVO;
import com.dawn.soul.service.HistoryService;
import com.dawn.soul.util.AuthUtil;

@Controller
@RequestMapping("/admin")
public class WorkHistoryController {

	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private AuthUtil authUtil;
	
	@RequestMapping(value="/work_history.do", method=RequestMethod.GET)
	public String workHistory(@ModelAttribute("menu_code") String menu_code, Model model, HttpSession session, 
			                  RedirectAttributes rttr) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		if(!authUtil.hasRole(loginUser.getAdminId(), "ROLE_SETTING_VIEW")) {
			rttr.addAttribute("menu_code", "01");
			rttr.addFlashAttribute("msg", "권한이 없습니다.");
			return "redirect:main.do";
		}
		List<HistoryVO> historyList = historyService.getHistoryList();
		model.addAttribute("historyList", historyList);
		return "admin/work_history";
	}
	
}
