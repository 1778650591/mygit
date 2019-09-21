package com.dorado.demo.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Page;
import com.dorado.demo.dao.SlEmployeeDao;
import com.dorado.demo.dao.SlMessageDao;
import com.dorado.demo.entity.SlEmployee;
import com.dorado.demo.entity.SlMessage;

@Component
@Transactional
public class MessageService {

	@Resource
	private SlMessageDao slMessageDao;
	@Resource
	private SlEmployeeDao slEmployeeDao;
	
	//保存message
	@DataResolver
	public void saveMessages(Collection<SlEmployee> slEmployees){
		for(SlEmployee employee:slEmployees){
			Collection<SlMessage> messages = employee.getSlMessageSet();
			for(SlMessage message : messages){
				//维护关联关系
				message.setSlEmployee(employee);
			}
			slMessageDao.persistEntities(messages);
		}
	}
	
	//根据用户id查询messages
	@DataProvider
	public void getMessageByEmployeeId(Page<SlMessage> page,Integer employeeId){
		String hql = "from SlMessage where slEmployee.employeeId = :employeeId";
		Map param = new HashMap();
		param.put("employeeId", employeeId);
		slMessageDao.find(page,hql,param);
	}
	
	//保存所有messages
	@DataResolver
	public void saveAll(Collection<SlEmployee> slEmployees){
		//维护主表信息
		slEmployeeDao.persistEntities(slEmployees);
		for(SlEmployee employee : slEmployees){
			Collection<SlMessage> messages = employee.getSlMessageSet();
			if(messages != null){
				for(SlMessage message : messages){
					//维护关联关系
					message.setSlEmployee(employee);
				}
				slMessageDao.persistEntities(messages);
			}
		}
		
	}
	
}








