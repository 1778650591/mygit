package com.dorado.demo.junior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.web.DoradoContext;
import com.dorado.demo.dao.SlEmployeeDao;
import com.dorado.demo.entity.SlEmployee;

@Component
@Transactional
public class LoginService {
	
	@Resource
	private SlEmployeeDao slEmployeeDao;
	
	//登录方法
	@Expose
	/*public Map doLogin(Map param){
		String username = (String)param.get("username");
		String password = (String)param.get("password");*/
	//简化写法  由dorado7拆解map的键值对 再进行匹配方法
	public Map doLogin(String username,String password){
		Map result = new HashMap();
		if(isValid(username, password)){
			//设置验证成功后跳转的页面
			result.put("url", "com.dorado.demo.main.Main.d");
			result.put("result",true);
			return result;
		}else{
			String errormsg = "用户名或密码不正确";
			result.put("errormsg", errormsg);
			result.put("result", false);
			return result;
		}
	}
	
	//以下方法需要替换为自己的验证用户名密码的业务逻辑
	public boolean isValid(String username,String password){
		//Hibernate离线条件查询
		DetachedCriteria dc = DetachedCriteria.forClass(SlEmployee.class);
		if(username != null && !"".equals(username)){
			//用户名条件查询
			dc.add(Restrictions.eq("userName",username.toUpperCase()));
		}
		//查询
		List<SlEmployee> employees = slEmployeeDao.find(dc);
		if(employees.size() > 0){
			SlEmployee employee = employees.get(0);
			//比对密码
			if(password.equals(employee.getPassword())){
				//验证成功后将用户信息放入session中
				DoradoContext ctx = DoradoContext.getCurrent();
				HttpServletRequest request = ctx.getRequest();
				request.getSession().setAttribute("user", employee);
//				request.getSession().setMaxInactiveInterval(30*60);
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	//退出登录
	@Expose
	public Map doLogout(){
		DoradoContext ctx = DoradoContext.getCurrent();
		HttpServletRequest request = ctx.getRequest();
		request.getSession().setAttribute("user", null);
		Map result = new HashMap();
		result.put("url", "com.dorado.demo.junior.Login.d");
		result.put("result", true);
		return result;
	}
	
	
	
}
