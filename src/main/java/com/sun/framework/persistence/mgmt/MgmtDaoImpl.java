package com.sun.framework.persistence.mgmt;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.sun.framework.persistence.GenericDao;
import com.sun.framework.persistence.GenericDaoImpl;

/**
 * 抽象数据访问实现对象
 * 
 * @版本 1.0
 */
public class MgmtDaoImpl<ENTITY, ID> extends GenericDaoImpl<ENTITY, ID> implements GenericDao<ENTITY, ID> {

    @Autowired
    @PersistenceContext(unitName = "RIS-RIM-MGMT")
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public MgmtDaoImpl(Class<?> entityClass) {
        super(entityClass);
    }

    /**
     * entityClass 实体类型
     */
    protected Class<?> entityClass;
}