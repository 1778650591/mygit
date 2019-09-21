package com.dorado.demo.dao;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.hibernate.HibernateDao;
import com.dorado.demo.entity.SlMessage;

@Repository
public class SlMessageDao extends HibernateDao<SlMessage, Long> {

}
