package com.dorado.demo.dao;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.hibernate.HibernateDao;
import com.dorado.demo.entity.SlDept;

@Repository
public class SlDeptDao extends HibernateDao<SlDept, Long> {

}
