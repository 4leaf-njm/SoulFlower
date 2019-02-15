package com.dawn.soul.controller.m.admin;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.dawn.soul.domain.HistoryVO;
import com.dawn.soul.service.HistoryService;
import com.dawn.soul.util.AuthUtil;

@Controller
@RequestMapping("/m/admin")
public class WorkHistoryControllerM {

	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private AuthUtil authUtil;
	
	@RequestMapping(value="/work_history.do", method=RequestMethod.GET)
	public String workHistory(@ModelAttribute("menu_code") String menu_code, @RequestParam(value="type", defaultValue="year") String type, 
	                          String date, Model model, HttpSession session, RedirectAttributes rttr) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		if(!authUtil.hasRole(loginUser.getAdminId(), "ROLE_SETTING_VIEW")) {
			rttr.addAttribute("menu_code", "01");
			rttr.addFlashAttribute("msg", "접근 권한이 없습니다.");
			return "redirect:main.do";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		if(date == null)
			date = sdf.format(new Date());
		
		List<HistoryVO> historyList = historyService.getSearchHistory(type, date);
		model.addAttribute("historyList", historyList);
		model.addAttribute("type", type);
		model.addAttribute("date", date);
		return "mobile/admin/work_history";
	}
	
	@RequestMapping(value="/searchHistory.do", method=RequestMethod.POST)
	public String searchHistory(String menu_code, String type, String date, RedirectAttributes rttr) throws SQLException {
		rttr.addAttribute("menu_code", menu_code);
		rttr.addAttribute("type", type);
		rttr.addAttribute("date", date);
		return "redirect:work_history.do";
	}
}
