package com.dawn.soul.controller.admin;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
import com.dawn.soul.domain.AreaVO;
import com.dawn.soul.domain.CompanyVO;
import com.dawn.soul.domain.ItemVO;
import com.dawn.soul.domain.SalesDataVO;
import com.dawn.soul.domain.SalesDetailVO;
import com.dawn.soul.domain.SalesListRequest;
import com.dawn.soul.domain.SalesVO;
import com.dawn.soul.service.AdminService;
import com.dawn.soul.service.AreaService;
import com.dawn.soul.service.CompanyService;
import com.dawn.soul.service.ItemService;
import com.dawn.soul.service.SalesService;
import com.dawn.soul.util.AuthUtil;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ItemService itemService;

	@Autowired
	private SalesService salesService;
	
	@Autowired
	private AuthUtil authUtil;
	
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String login(@ModelAttribute("msg") String msg) {
		return "admin/login";
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login(AdminVO admin, Model model, HttpSession session) throws SQLException {
		int result = adminService.getLoginResult(admin);
		if(result == -1) {
			model.addAttribute("msg", "아이디가 존재하지 않습니다.");
			model.addAttribute("admin", admin);
			return "admin/login";
		} else if (result == 0) {
			model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
			model.addAttribute("admin", admin);
			return "admin/login";
		} else if (result == 1){
			AdminVO loginUser = adminService.getAdminById(admin.getAdminId()); 
			session.setAttribute("loginUser", loginUser);
			return "redirect:main.do?menu_code=01";
		} else {
			model.addAttribute("msg", "관리자 승인이 필요합니다.");
			model.addAttribute("admin", admin);
			return "admin/login";
		} 
	}
	
	@RequestMapping(value="/logout.do", method=RequestMethod.GET)
	public String logout(HttpSession session, RedirectAttributes rttr) {
		session.invalidate();
		rttr.addFlashAttribute("msg", "로그아웃 되었습니다.");
		return "redirect:login.do";
	}
	
	@RequestMapping(value="/join.do", method=RequestMethod.GET)
	public String join() {
		return "admin/join";
	}
	
	@RequestMapping(value="/join.do", method=RequestMethod.POST)
	public String join(AdminVO admin, RedirectAttributes rttr, Model model) throws SQLException {
		AdminVO user = adminService.getAdminById(admin.getAdminId());
		if(user == null) {
			adminService.insertAdmin(admin);
			rttr.addFlashAttribute("msg", "회원가입 되었습니다. 관리자 승인 후 로그인 할 수 있습니다.");
			return "redirect:login.do";
		} else {
			model.addAttribute("msg", "중복된 아이디입니다.");
			model.addAttribute("admin", admin);
			return "admin/join";
		}
		
	}
	
	@RequestMapping(value="/main.do", method=RequestMethod.GET)
	public String main(@ModelAttribute("menu_code") String menu_code, @RequestParam(value="type", defaultValue="daily") String type, 
			           String date, String areaCheckList, Model model, HttpSession session) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		if(!authUtil.hasRole(loginUser.getAdminId(), "ROLE_SALES_VIEW")) {
			model.addAttribute("auth", "N");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(date == null)
			date = sdf.format(new Date());
		List<SalesVO> salesList = salesService.getSalesList();
		Set<String> areaList = new TreeSet<String>();
		for(SalesVO sales : salesList) 
			areaList.add(sales.getAreaName());
		
		Map<String, SalesDataVO> salesDataMap = new HashMap<String, SalesDataVO>();
		List<SalesVO> salesSearchList = salesService.getSearchSales(type, date, areaCheckList);
		Set<String> itemList = new TreeSet<String>();
		Set<String> companyList = new HashSet<String>();
		int totalProfit = 0, totalRebate = 0;
		if(salesSearchList != null) {
			for(int i=0; i<salesSearchList.size(); i++) {
				SalesVO sales = salesSearchList.get(i);
				List<SalesDetailVO> salesDetList = salesService.getSalesDetList(sales.getSalesNo()); 
				for(SalesDetailVO salesDet : salesDetList)
					itemList.add(salesDet.getItemName());
				companyList.add(sales.getCompanyName());
			}
			Iterator<String> companyItr = companyList.iterator();
			while(companyItr.hasNext()) {
				String company = companyItr.next();
				List<SalesVO> salesList2 = new ArrayList<SalesVO>();
				for(int i=0; i<salesSearchList.size(); i++) {
					SalesVO sales = salesSearchList.get(i);
					if(company.equals(sales.getCompanyName())) {
						salesList2.add(sales);
					}
				}
				Map<Integer, List<SalesDetailVO>> salesDetMap = new HashMap<Integer, List<SalesDetailVO>>();
				List<Integer> amountList = new ArrayList<Integer>(itemList.size());
				for(int i=0; i<itemList.size(); i++) 
					amountList.add(0);
				
				int profit = 0, rebate = 0;
				for(int i=0; i<salesList2.size(); i++) {
					SalesVO sales = salesList2.get(i);
					List<SalesDetailVO> salesDetList = salesService.getSalesDetList(sales.getSalesNo());
					for(SalesDetailVO det : salesDetList) {
						profit += det.getProfit();
						rebate += det.getRebate();
						totalProfit += det.getProfit();
						totalRebate += det.getRebate();
						
						int idx = 0;
						Iterator<String> itemItr = itemList.iterator();
						while(itemItr.hasNext()) {
							String item = itemItr.next();
							if(item.equals(det.getItemName())) {
								amountList.set(idx, amountList.get(idx) + det.getAmount());
							}
							idx ++;
						}
					}
					salesDetMap.put(sales.getSalesNo(), salesDetList);
				}
				String[] itemUnit = new String[itemList.size()];
				List<ItemVO> itemAllList = itemService.getItemList();
				int idx = 0;
				for(String itemName : itemList) {
					for(ItemVO item : itemAllList) {
						if(itemName.equals(item.getItemName())) {
							itemUnit[idx] = item.getItemUnit();
							break;
						}
					}
					idx ++;
				}
				List<String> amountList2 = new ArrayList<String>();
				for(int i=0; i<amountList.size(); i++) {
					int amount = amountList.get(i);
					String unit = itemUnit[i];
					amountList2.add(amount + " " + unit);
				}
				SalesDataVO salesData = new SalesDataVO();
				salesData.setSalesList(salesList2);
				salesData.setAmountList(amountList2);
				salesData.setSalesDetMap(salesDetMap);
				salesData.setProfit(profit);
				salesData.setRebate(rebate);
				salesData.setRealProfit(profit - rebate);
				salesDataMap.put(company, salesData);
			}
		}
		model.addAttribute("areaList", areaList);
		model.addAttribute("type", type);
		model.addAttribute("date", date);
		model.addAttribute("areaCheckList", areaCheckList);
		model.addAttribute("itemList", itemList);
		model.addAttribute("itemCheckList", itemList);
		model.addAttribute("companyList", companyList);
		model.addAttribute("salesDataMap", salesDataMap);
		model.addAttribute("totalProfit", totalProfit);
		model.addAttribute("totalRebate", totalRebate);
		model.addAttribute("totalRealProfit", totalProfit - totalRebate);
		
		return "admin/main";
	}
	
	@RequestMapping(value="/register.do", method=RequestMethod.GET)
	public String register(@ModelAttribute("menu_code") String menu_code, Model model, HttpSession session) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		if(!authUtil.hasRole(loginUser.getAdminId(), "ROLE_SALES_REGISTER")) {
			model.addAttribute("msg", "권한이 없습니다.");
			model.addAttribute("auth", "N");
		}
		List<AreaVO> areaList = areaService.getAreaList();
		List<CompanyVO> companyList = companyService.getCompanyList();
		List<ItemVO> itemList = itemService.getItemList();
		
		model.addAttribute("areaList", areaList);
		model.addAttribute("companyList", companyList);
		model.addAttribute("itemList", itemList);
		
		return "admin/register";
	}
	
	@RequestMapping(value="/register.do", method=RequestMethod.POST)
	public String register(String menu_code, SalesVO sales, SalesListRequest salesList, HttpSession session, 
			               RedirectAttributes rttr) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		salesService.insertSales(loginUser, sales, salesList.getSalesList());
		
		rttr.addAttribute("menu_code", menu_code);
		rttr.addFlashAttribute("msg", "등록이 완료되었습니다.");
		return "redirect:register.do";
	}
	
	@RequestMapping(value="/modifySales.do", method=RequestMethod.POST)
	public String modifySales(String menu_code, SalesVO sales, SalesListRequest salesList, HttpSession session, 
			               RedirectAttributes rttr) throws SQLException {
		AdminVO loginUser = (AdminVO) session.getAttribute("loginUser");
		System.out.println(sales);
		System.out.println(salesList);
		salesService.modifySales(loginUser, sales, salesList.getSalesList());
		
		rttr.addAttribute("menu_code", menu_code);
		rttr.addFlashAttribute("msg", "변경이 완료되었습니다.");
		return "redirect:register.do";
	}
	
	@RequestMapping(value="/searchSale.do", method=RequestMethod.POST)
	public String searchSale(String menu_code, String type, String date, String[] areaList, 
			                 RedirectAttributes rttr) throws SQLException {
		String areaCheckList = "";
		for(int i=0; i<areaList.length; i++)
			areaCheckList += i == areaList.length-1 ? areaList[i] : areaList[i] + ", ";

		rttr.addAttribute("menu_code", menu_code);
		rttr.addAttribute("type", type);
		rttr.addAttribute("date", date);
		rttr.addAttribute("areaCheckList", areaCheckList);
		return "redirect:main.do";
	}
	
	@RequestMapping(value="/search.do", method=RequestMethod.POST)
	public String search(@ModelAttribute("menu_code") String menu_code, @RequestParam(value="type", defaultValue="daily") String type, 
			             String date, String areaCheckList, String[] companyList, String[] itemList, Model model) throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(date == null)
			date = sdf.format(new Date());
		List<String> itemCheckList = new ArrayList<String>();
		for(String i : itemList)
			itemCheckList.add(i);
		
		List<SalesVO> salesList = salesService.getSalesList();
		Set<String> areaList = new TreeSet<String>();
		for(SalesVO sales : salesList) 
			areaList.add(sales.getAreaName());
		
		Map<String, SalesDataVO> salesDataMap = new HashMap<String, SalesDataVO>();
		List<SalesVO> salesSearchList = salesService.getSearchSales(type, date, areaCheckList);
		Set<String> itemList2 = new TreeSet<String>();
		Set<String> companyList2 = new HashSet<String>();
		int totalProfit = 0, totalRebate = 0;
		if(salesSearchList != null) {
			for(int i=0; i<salesSearchList.size(); i++) {
				SalesVO sales = salesSearchList.get(i);
				List<SalesDetailVO> salesDetList = salesService.getSalesDetList(sales.getSalesNo()); 
				for(SalesDetailVO salesDet : salesDetList)
					itemList2.add(salesDet.getItemName());
				companyList2.add(sales.getCompanyName());
			}
			Iterator<String> companyItr = companyList2.iterator();
			while(companyItr.hasNext()) {
				String company = companyItr.next();
				
				if(companyList.length != 0) {
					boolean flag = false;
					for(String comp : companyList) {
						if(company.equals(comp)) {
							flag = true;
							break;
						}
					}
					if(!flag) continue;
				}
				
				List<SalesVO> salesList2 = new ArrayList<SalesVO>();
				for(int i=0; i<salesSearchList.size(); i++) {
					SalesVO sales = salesSearchList.get(i);
					if(company.equals(sales.getCompanyName())) {
						salesList2.add(sales);
					}
				}
				Map<Integer, List<SalesDetailVO>> salesDetMap = new HashMap<Integer, List<SalesDetailVO>>();
				List<Integer> amountList = new ArrayList<Integer>(itemList.length);
				for(int i=0; i<itemList.length; i++) 
					amountList.add(0);
				
				int profit = 0, rebate = 0;
				Iterator<SalesVO> salesItr = salesList2.iterator();
				while(salesItr.hasNext()) {
					SalesVO sales = salesItr.next();
					List<SalesDetailVO> salesDetList = salesService.getSalesDetList(sales.getSalesNo());
					Iterator<SalesDetailVO> detItr = salesDetList.iterator();
					while(detItr.hasNext()) {
						SalesDetailVO det = detItr.next();
						boolean flag = false;
						Iterator<String> itemItr = itemCheckList.iterator();
						while(itemItr.hasNext()) {
							String item = itemItr.next();
							if(item.equals(det.getItemName())) {
								flag = true;
								break;
							}
						}
						if(!flag) {
							detItr.remove();
							continue;
						}
						profit += det.getProfit();
						rebate += det.getRebate();
						totalProfit += det.getProfit();
						totalRebate += det.getRebate();
						
						int idx = 0;
						for(String item : itemList) {
							if(item.equals(det.getItemName())) {
								amountList.set(idx, amountList.get(idx) + det.getAmount());
							}
							idx ++;
						}
					}
					if(salesDetList.isEmpty()) {
						salesItr.remove();
						continue;
					}
					salesDetMap.put(sales.getSalesNo(), salesDetList);
				}
				String[] itemUnit = new String[itemList2.size()];
				List<ItemVO> itemAllList = itemService.getItemList();
				int idx = 0;
				for(String itemName : itemList) {
					for(ItemVO item : itemAllList) {
						if(itemName.equals(item.getItemName())) {
							itemUnit[idx] = item.getItemUnit();
							break;
						}
					}
					idx ++;
				}
				List<String> amountList2 = new ArrayList<String>();
				for(int i=0; i<amountList.size(); i++) {
					int amount = amountList.get(i);
					String unit = itemUnit[i];
					amountList2.add(amount + " " + unit);
				}
				if(salesList2.isEmpty()) continue;
				SalesDataVO salesData = new SalesDataVO();
				salesData.setSalesList(salesList2);
				salesData.setAmountList(amountList2);
				salesData.setSalesDetMap(salesDetMap);
				salesData.setProfit(profit);
				salesData.setRebate(rebate);
				salesData.setRealProfit(profit - rebate);
				salesDataMap.put(company, salesData);
			}
		}
		
		String companyCheckList = "";
		for(int i=0; i<companyList.length; i++)
			companyCheckList += i == companyList.length-1 ? companyList[i] : companyList[i] + ", "; 

			model.addAttribute("areaList", areaList);
		model.addAttribute("type", type);
		model.addAttribute("date", date);
		model.addAttribute("areaCheckList", areaCheckList);
		model.addAttribute("itemList", itemList2);
		model.addAttribute("itemCheckList", itemCheckList);
		model.addAttribute("companyList", companyList2);
		model.addAttribute("companyCheckList", companyCheckList);
		model.addAttribute("salesDataMap", salesDataMap);
		model.addAttribute("totalProfit", totalProfit);
		model.addAttribute("totalRebate", totalRebate);
		model.addAttribute("totalRealProfit", totalProfit - totalRebate);
		
		return "admin/main";
	}
	
	@ResponseBody
	@RequestMapping(value="/getSalesByDate.do", method=RequestMethod.POST)
	public List<SalesVO> getSalesByDate(@RequestParam("date") String salesDate) throws SQLException {
		return salesService.getSalesListByDate(salesDate);
	}
	
	@ResponseBody
	@RequestMapping(value="/getSales.do", method=RequestMethod.POST)
	public Map<Object, Object> getSales(Integer salesNo) throws SQLException {
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		SalesVO sales = salesService.getSalesByNo(salesNo);
		List<SalesDetailVO> salesDetList = salesService.getSalesDetList(salesNo);
		dataMap.put(sales, salesDetList);
		return dataMap;
	}
}