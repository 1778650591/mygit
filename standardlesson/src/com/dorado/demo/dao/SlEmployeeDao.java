package com.dorado.demo.dao;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.hibernate.HibernateDao;
import com.dorado.demo.entity.SlEmployee;

@Repository
public class SlEmployeeDao extends HibernateDao<SlEmployee, Long> {

}
