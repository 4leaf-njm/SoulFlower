package com.dawn.soul.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dawn.soul.util.AuthUtil;

@Controller
@RequestMapping("/admin")
public class WorkHistoryController {
	
	@Autowired
	private AuthUtil authUtil;
	
}
