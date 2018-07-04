package com.dawn.soul.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(request.getSession().getAttribute("loginUser") == null) {
			String uri = request.getRequestURI();
			String ctx = request.getContextPath();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.println("<script>alert('로그인 후 이용가능합니다.');</script>");
			
			if(uri.contains("/m/")) 
				writer.println("<script>location.href='" + ctx + "/m/admin/login.do';</script>");
			else
				writer.println("<script>location.href='" + ctx + "/admin/login.do';</script>");
			writer.flush();
			writer.close();
			return false;
		}
		return true;
	}
}
