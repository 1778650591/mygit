package com.dorado.middle;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.web.DoradoContext;
import com.dorado.demo.dao.SlEmployeeDao;
import com.dorado.demo.entity.SlEmployee;

@Component
@Transactional
public class LoginServiceForMiddle {

	@Resource
	private SlEmployeeDao slEmployeeDao;
	
	@Expose
	public Map doLogout(){
		DoradoContext ctx = DoradoContext.getCurrent();
		HttpServletRequest request = ctx.getRequest();
		request.getSession().setAttribute("user", null);
		Map result = new HashMap();
		result.put("url", "com.dorado.middle.Login.d");
		result.put("result", true);
		return result;
	}
	
	@Expose
	public void changePassWord(String newPassWord){
		DoradoContext dc = DoradoContext.getCurrent();
		HttpSession session = dc.getRequest().getSession();
		SlEmployee employee = (SlEmployee)session.getAttribute("user");
		employee.setPassword(newPassWord);
		slEmployeeDao.getSession().update(employee);
	}
	
}






