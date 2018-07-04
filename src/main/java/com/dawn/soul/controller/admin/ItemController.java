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
import com.dawn.soul.domain.ItemVO;
import com.dawn.soul.service.ItemService;
import com.dawn.soul.util.AuthUtil;

@Controller
@RequestMapping("/admin")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private AuthUtil authUtil;
	
	@RequestMapping(value="/item.do", method=RequestMethod.GET)
	public String item(@ModelAttribute("menu_code") String menu_code, Model model, HttpSession session,
		               RedirectAttributes rttr) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		if(!authUtil.hasRole(loginUser.getAdminId(), "ROLE_SETTING_VIEW")) {
			rttr.addAttribute("menu_code", "01");
			rttr.addFlashAttribute("msg", "권한이 없습니다.");
			return "redirect:main.do";
		}
		List<ItemVO> itemList = itemService.getItemList(); 
		
		model.addAttribute("itemList", itemList);
		
		return "admin/item";
	}
	
	@RequestMapping(value="/item.do", method=RequestMethod.POST)
	public String item(String menu_code, ItemVO item, RedirectAttributes rttr) throws SQLException {
		itemService.insertItem(item);
		rttr.addAttribute("menu_code", menu_code);
		return "redirect:item.do";
	}
	
	@RequestMapping(value="/removeItem.do", method=RequestMethod.GET)
	public String removeItem(String menu_code, @RequestParam("no") int itemNo, RedirectAttributes rttr) throws SQLException {
		itemService.removeItem(itemNo);
		rttr.addAttribute("menu_code", menu_code);
		return "redirect:item.do";
	}
	
	@ResponseBody
	@RequestMapping(value="/ajaxGetItem.do", method=RequestMethod.POST)
	public ItemVO ajaxGetItem(@RequestParam("no") int itemNo) throws SQLException {
		return itemService.getItemById(itemNo);
	}
}
