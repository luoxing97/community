package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

@Repository("alphaHibernate")
public class AlphaDaoHibernateImpl implements AlphaDao {
    @Override
    public String select() {
        System.out.println("-----------");
        System.out.println("AlphaDaoHibernateImpl");
        return "Hibernate";
    }
}
