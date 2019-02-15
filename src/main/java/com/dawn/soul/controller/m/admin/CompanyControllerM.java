package com.dawn.soul.controller.m.admin;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dawn.soul.domain.AdminVO;
import com.dawn.soul.domain.CompanyVO;
import com.dawn.soul.domain.HistoryVO;
import com.dawn.soul.service.CompanyService;
import com.dawn.soul.service.HistoryService;
import com.dawn.soul.util.AuthUtil;
import com.dawn.soul.util.KeyUtil;

@Controller
@RequestMapping("/m/admin")
public class CompanyControllerM {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private AuthUtil authUtil;
	
	@RequestMapping(value="/company.do", method=RequestMethod.GET)
	public String company(@ModelAttribute("menu_code") String menu_code, Model model, HttpSession session, RedirectAttributes rttr) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		if(!authUtil.hasRole(loginUser.getAdminId(), "ROLE_SETTING_VIEW")) {
			rttr.addAttribute("menu_code", "01");
			rttr.addFlashAttribute("msg", "접근 권한이 없습니다.");
			return "redirect:main.do";
		}
		List<CompanyVO> companyList = companyService.getCompanyList(); 
		
		model.addAttribute("companyList", companyList);
		
		return "mobile/admin/company";
	}
	
	@RequestMapping(value="/company.do", method=RequestMethod.POST)
	public String company(String menu_code, CompanyVO company, HttpSession session, RedirectAttributes rttr) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		companyService.insertCompany(company);
		
		String text = loginUser.getAdminName() + " (" + loginUser.getAdminId() + ") 님이 " + "상조회사 관리에서 " + company.getCompanyName() + "를 추가했습니다.";
		HistoryVO history = new HistoryVO();
		history.setHistoryText(text);
		historyService.insertHistory(history);
		
		rttr.addAttribute("menu_code", menu_code);
		return "redirect:company.do";
	}
	
	@RequestMapping(value="/removeCompany.do", method=RequestMethod.GET)
	public String company(String menu_code, @RequestParam("no") int companyNo, HttpSession session, RedirectAttributes rttr) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		CompanyVO company = companyService.getCompanyById(companyNo);
		companyService.removeCompany(companyNo);
		
		String text = loginUser.getAdminName() + " (" + loginUser.getAdminId() + ") 님이 " + "상조회사 관리에서 " + company.getCompanyName() + "를 삭제했습니다.";
		HistoryVO history = new HistoryVO();
		history.setHistoryText(text);
		historyService.insertHistory(history);
		
		rttr.addAttribute("menu_code", menu_code);
		return "redirect:company.do";
	}
	
	@ResponseBody
	@RequestMapping(value="/searchCompany.do", method=RequestMethod.POST)
	public List<CompanyVO> search(@RequestParam("word") String str) throws SQLException {
		String word = KeyUtil.getKoToChar(str);
		List<CompanyVO> companyList = companyService.getSearchCompany(word);
		return companyList;
	}
}
