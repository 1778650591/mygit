package com.dorado.middle;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.dorado.demo.dao.SlEmployeeDao;
import com.dorado.demo.entity.SlEmployee;

public class LoginActionResolver extends AbstractController{

	@Resource
	private SlEmployeeDao slEmployeeDao;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		String password = request.getParameter("password");	
		SlEmployee employee = isValid(username, password);
		if(employee != null){
			request.getSession().setAttribute("user", employee);
			return new ModelAndView("com.dorado.middle.main.Main.d");
		}else{
			String errormsg = "用户名或密码不正确";
			ModelAndView mav = new ModelAndView("com.dorado.middle.Login.d");
			mav.addObject("errormsg", errormsg);
			return mav;
		}
	}
	
	
	//验证用户名密码逻辑
	public SlEmployee isValid(String username,String password){
		DetachedCriteria dc = DetachedCriteria.forClass(SlEmployee.class);
		if(username != null && !"".equals(username)){
			dc.add(Restrictions.eq("userName", username.toUpperCase()));
		}
		List<SlEmployee> employees = slEmployeeDao.find(dc);
		for(SlEmployee employee: employees){
			if(password.equals(employee.getPassword())){
				return employee;
			}else{
				return null;
			}
		}
		return null;
	}
	

}
