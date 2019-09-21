package com.dorado.demo.service;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.dorado.demo.dao.SlCompanyDao;
import com.dorado.demo.entity.SlCompany;

@Component
@Transactional	//注意加事务，教程上没有
public class CompanyService {

	@Resource
	private SlCompanyDao slCompanyDao;
	
	//查询集合
	@DataProvider
	public Collection<SlCompany> getCompany(){
		return slCompanyDao.getAll();
	}
	//查询单个
	@DataProvider
	/*public SlCompany getCompany(){
		List<SlCompany> list = slCompanyDao.getAll();
		for(SlCompany company : list){
			return company;
		}
		return null;
	}*/
	
	//保存单个数据
	@DataResolver
	public void saveCompany(SlCompany slcompanys){
		slCompanyDao.persistEntity(slcompanys);
    }
	
	//保存多个数据
	public void saveCompany(List<SlCompany> slcompanys){
		slCompanyDao.persistEntities(slcompanys);
    }
	
	
}
