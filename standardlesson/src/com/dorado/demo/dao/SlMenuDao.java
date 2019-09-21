package com.dorado.demo.dao;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.hibernate.HibernateDao;
import com.dorado.demo.entity.SlMenu;

@Repository
public class SlMenuDao extends HibernateDao<SlMenu, Long> {

}
