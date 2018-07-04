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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dawn.soul.domain.AdminVO;
import com.dawn.soul.domain.CompanyVO;
import com.dawn.soul.service.CompanyService;
import com.dawn.soul.util.AuthUtil;
import com.dawn.soul.util.KeyUtil;

@Controller
@RequestMapping("/admin")
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private AuthUtil authUtil;
	
	@RequestMapping(value="/company.do", method=RequestMethod.GET)
	public String company(@ModelAttribute("menu_code") String menu_code, Model model, HttpSession session,
			              RedirectAttributes rttr) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		if(!authUtil.hasRole(loginUser.getAdminId(), "ROLE_SETTING_VIEW")) {
			rttr.addAttribute("menu_code", "01");
			rttr.addFlashAttribute("msg", "권한이 없습니다.");
			return "redirect:main.do";
		}
		List<CompanyVO> companyList = companyService.getCompanyList(); 
		
		model.addAttribute("companyList", companyList);
		
		return "admin/company";
	}
	
	@RequestMapping(value="/company.do", method=RequestMethod.POST)
	public String company(String menu_code, CompanyVO company, RedirectAttributes rttr) throws SQLException {
		companyService.insertCompany(company);
		rttr.addAttribute("menu_code", menu_code);
		return "redirect:company.do";
	}
	
	@RequestMapping(value="/removeCompany.do", method=RequestMethod.GET)
	public String company(String menu_code, @RequestParam("no") int companyNo, RedirectAttributes rttr) throws SQLException {
		companyService.removeCompany(companyNo);
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
