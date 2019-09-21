package com.dorado.demo.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;

@Component
@Transactional
public class MarriedDropDownInterceptor {
	
	@DataProvider
	public Map<String,String> getMarriedState(){
		Map<String,String> mapValue = new LinkedHashMap<String,String>();
		mapValue.put("true", "已婚");
		mapValue.put("false", "未婚");
		return mapValue;
	}
	
}
