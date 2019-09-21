package com.dorado.demo.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.hibernate.HibernateUtils;
import com.dorado.demo.dao.SlEmployeeDao;
import com.dorado.demo.entity.SlEmployee;

@Component
@Transactional
public class EmployeeService {

	@Resource
	private SlEmployeeDao slEmployeeDao;
	
	//查询全部
	@DataProvider
	public Collection<SlEmployee> getAll(){
		return slEmployeeDao.getAll();
	}
	
	//分页查询
	@DataProvider
	public void getAllForPage(Page<SlEmployee> page){
		slEmployeeDao.getAll(page);
	}
	
	//过滤查询
	@DataProvider
	public void getAllForFilter(Page<SlEmployee> page,Criteria criteria)throws Exception{
		//Hibernate离线查询
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SlEmployee.class);
		if(criteria != null){
			slEmployeeDao.find(page,HibernateUtils.createFilter(detachedCriteria, criteria));
		}else{
			slEmployeeDao.getAll(page);
		}
		
	}
	
	//按条件查询
	@DataProvider
	public void queryForCondition(Page<SlEmployee> page,Map<String,Object> params){
		if(null != params){
			String employeeCode = (String)params.get("employeeCode");
			String employeeName = (String)params.get("employeeName");
			String userName = (String)params.get("userName");
			String email = (String)params.get("email");
			String phone = (String)params.get("phone");
			String mobile = (String)params.get("mobile");
			String position = (String)params.get("position");
			
			String whereCase = "";
			
			if(StringHelper.isNotEmpty(employeeCode)){
				whereCase += "AND employeeCode like '%" + employeeCode + "%'";
			}
			
			if(StringHelper.isNotEmpty(employeeName)){
				whereCase += "AND employeeName like '%" + employeeName + "%'";
			}
			
			if(StringHelper.isNotEmpty(userName)){
				whereCase += "AND userName like '%" + userName + "%'";
			}
			
			if(StringHelper.isNotEmpty(email)){
				whereCase += "AND email like '%" + email + "%'";
			}
			
			if(StringHelper.isNotEmpty(phone)){
				whereCase += "AND phone like '%" + phone + "%'";
			}
			
			if(StringHelper.isNotEmpty(mobile)){
				whereCase += "AND mobile like '%" + mobile + "%'";
			}
			
			if(StringHelper.isNotEmpty(position)){
				whereCase += "AND position like '%" + position + "%'";
			}
			
			slEmployeeDao.find(page," from SlEmployee where 1=1 " + whereCase);
		}else{
			slEmployeeDao.getAll(page);
		}
	}
	
	//批量保存
	@DataResolver
	public void saveAll(Collection<SlEmployee> slEmployees){
		slEmployeeDao.persistEntities(slEmployees);
	}
	
	//根据用户名查询用户信息
	@DataProvider
	public Collection<SlEmployee> getEmployeeByUserName(String username){
		Map param = new HashMap();
		if(StringHelper.isNotEmpty(username)){
			param.put("userName", username);
			return slEmployeeDao.find("from SlEmployee where userName = :userName", param);
		}else{
			return null;
		}
	}
	
	
}












