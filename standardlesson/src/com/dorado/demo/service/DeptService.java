package com.dorado.demo.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Page;
import com.dorado.demo.dao.SlDeptDao;
import com.dorado.demo.dao.SlEmployeeDao;
import com.dorado.demo.entity.SlDept;
import com.dorado.demo.entity.SlEmployee;

@Component
@Transactional
public class DeptService {

	@Resource
	private SlDeptDao slDeptDao;
	@Resource
	private SlEmployeeDao slEmployeeDao;
	
	
	@DataProvider
	public Collection<SlDept> getTopDept(){
		return slDeptDao.find("from SlDept where slDept.deptId is null");
	}
	
	@DataProvider
	public Collection<SlDept> getDeptByParentId(Integer parentId){
		if(null != parentId){
			String hql = "from SlDept where slDept.deptId = :deptId";
			Map param = new HashMap();
			param.put("deptId", parentId);
			return slDeptDao.find(hql,param);
		}else{
			return null;
		}
	}
	
	@DataResolver
	public void saveAll(Collection<SlDept> depts){
		for(SlDept dept : depts){
			slDeptDao.persistEntity(dept);
			Collection<SlDept> childs = dept.getSlDeptSet();
			if(!(childs == null)){
				for(SlDept child : childs){
					//维护关联关系
					child.setSlDept(dept);
				}
				slDeptDao.persistEntities(childs);
				saveAll(childs);
			}
			
		}
	}
	
	//根据部门id查询部门下所有员工
	@DataProvider
	public void getEmployeeByDeptId(Page<SlEmployee> page,Integer deptId){
		if(null != deptId){
			String hql = "from SlEmployee where slDept.deptId = :deptId";
			Map param = new HashMap();
			param.put("deptId", deptId);
			slEmployeeDao.find(page,hql,param);
		}
	}
	
	//查询所有
	@DataProvider
	public Collection<SlDept> getAll(){
		return slDeptDao.getAll();
	}
	
	//部门名称过滤
	@DataProvider
	public Collection<SlDept> getDeptByDeptName(String deptName){
		String hql = "from SlDept where deptName like :deptName";
		Map param = new HashMap();
		if(StringHelper.isNotEmpty(deptName)){
			param.put("deptName", "%"+deptName+"%");
			return slDeptDao.find(hql,param);
		}else{
			return slDeptDao.getAll();
		}
	}
	
	
}















