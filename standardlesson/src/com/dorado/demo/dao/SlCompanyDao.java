package com.dorado.demo.dao;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.hibernate.HibernateDao;
import com.dorado.demo.entity.SlCompany;

@Repository
public class SlCompanyDao extends HibernateDao<SlCompany, Long> {

}
