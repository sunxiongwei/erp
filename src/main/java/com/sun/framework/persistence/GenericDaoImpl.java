package com.sun.framework.persistence;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.sun.framework.persistence.GenericDao;
import com.sun.framework.persistence.QLBuilder;
import com.sun.framework.persistence.entity.AbstractEntity;
import com.sun.framework.toolbox.Pagination;

/**
 * 抽象数据访问实现对象
 * 
 * @版本 1.0
 */
public abstract class GenericDaoImpl<ENTITY, ID> implements GenericDao<ENTITY, ID> {

    /**
     * entityClass 实体类型
     */
    protected Class<?> entityClass;

    /**
     * 默认构造函数
     * 
     * @param entityClass 当前实体类型
     */
    public GenericDaoImpl(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * 默认构造函数
     * 
     * @param entityClass 当前实体类型
     */

    public abstract EntityManager getEntityManager();
    

    @Override
    public AbstractEntity<?> save(AbstractEntity<?> entity) {
        if (entity.isNew()) {
            this.getEntityManager().persist(entity);
        } else {
            this.getEntityManager().merge(entity);
        }
        return entity;
    }

    public void insert(ENTITY entity) {
        // TODO Auto-generated method stub
        this.getEntityManager().persist(entity);
    }


    public ENTITY update(ENTITY entity) {
        // TODO Auto-generated method stub
        return this.getEntityManager().merge(entity);
    }


    public int updateByJpql(String jpql, Object...params) {
        // TODO Auto-generated method stub
        Query query = this.createQuery(jpql, params);

        return query.executeUpdate();
    }


    @Override
    public int updateByJpql(QLBuilder builder) {
        // TODO Auto-generated method stub
        if (builder.getMainClause() == null) {
            builder.setMainClause("UPDATE " + this.entityClass.getSimpleName());
        }

        Query query = this.getEntityManager().createQuery(builder.getFullQL());

        Map<String, Object> params = builder.getParams();

        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        return query.executeUpdate();
    }


    public int updateBySql(String sql, Object...params) {
        // TODO Auto-generated method stub
        Query query = this.createNativeQuery(sql, params);

        return query.executeUpdate();
    }


    @Override
    public int updateBySql(QLBuilder builder) {
        // TODO Auto-generated method stub

        if (builder.getMainClause() == null) {
            builder.setMainClause("UPDATE " + this.getEntityTableName());
        }

        Query query = this.getEntityManager().createNativeQuery(builder.getFullQL());

        Map<String, Object> params = builder.getParams();

        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        return query.executeUpdate();
    }


    public void delete(ID id) {
        // TODO Auto-generated method stub
        Object o = this.getEntityManager().find(this.entityClass, id);
        if (o != null) {
            this.getEntityManager().remove(o);
        }
        // this.getEntityManager().remove(this.getEntityManager().find(this.entityClass, id));
    }


    public int deleteByJpql(String jpql, Object...params) {
        // TODO Auto-generated method stub
        return this.updateByJpql(jpql, params);
    }


    @Override
    public int deleteByJpql(QLBuilder builder) {
        // TODO Auto-generated method stub
        if (builder.getMainClause() == null) {
            builder.setMainClause("DELETE FROM " + this.entityClass.getSimpleName() + " AS obj");
        }

        Query query = this.getEntityManager().createQuery(builder.getFullQL());

        Map<String, Object> params = builder.getParams();

        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        return query.executeUpdate();
    }


    public int deleteBySql(String sql, Object...params) {
        // TODO Auto-generated method stub
        return this.updateBySql(sql, params);
    }


    @Override
    public int deleteBySql(QLBuilder builder) {
        // TODO Auto-generated method stub
        if (builder.getMainClause() == null) {
            builder.setMainClause("DELETE FROM " + this.getEntityTableName());
        }

        Query query = this.getEntityManager().createNativeQuery(builder.getFullQL());

        Map<String, Object> params = builder.getParams();

        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        return query.executeUpdate();
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> T getOneById(ID id) {
        // TODO Auto-generated method stub
        return (T) this.getEntityManager().find(entityClass, id);
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> T getOneByJpql(String jpql, Object...params) {
        // TODO Auto-generated method stub
        Query query = this.createQuery(jpql, params);

        return (T) query.getSingleResult();
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> T getOneByJpql(QLBuilder builder) {
        // TODO Auto-generated method stub
        if (builder.getMainClause() == null) {
            builder.setMainClause("SELECT obj FROM " + this.entityClass.getSimpleName() + " AS obj");
        }

        Query query = this.getEntityManager().createQuery(builder.getFullQL());

        Map<String, Object> params = builder.getParams();

        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        return (T) query.getSingleResult();
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> T getOneBySql(String sql, Object...params) {
        // TODO Auto-generated method stub
        Query query = this.createNativeQuery(sql, params);

        return (T) query.getSingleResult();
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> T getOneBySql(QLBuilder builder) {
        // TODO Auto-generated method stub
        if (builder.getMainClause() == null) {
            builder.setMainClause("SELECT * FROM " + this.getEntityTableName());
        }

        Query query = this.getEntityManager().createNativeQuery(builder.getFullQL());

        Map<String, Object> params = builder.getParams();

        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        return (T) query.getSingleResult();
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> T selectOneById(ID id) {
        // TODO Auto-generated method stub
        try {
            return (T) this.getEntityManager().find(entityClass, id);
        } catch (EntityNotFoundException ex) {
            return null;
        }
    }


    @Override
    public <T> T selectOneByJpql(String jpql, Object...params) {
        // TODO Auto-generated method stub

        try {
            return this.getOneByJpql(jpql, params);
        } catch (NoResultException ex) {
            return null;
        }

    }


    @Override
    public <T> T selectOneByJpql(QLBuilder builder) {
        // TODO Auto-generated method stub

        try {
            return this.getOneByJpql(builder);
        } catch (NoResultException ex) {
            return null;
        }

    }


    @SuppressWarnings("unchecked")
    public <T> T selectOneBySql(String sql, Object...params) {
        // TODO Auto-generated method stub
        try {
            return (T) this.getOneBySql(sql, params);
        } catch (NoResultException ex) {
            return null;
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> T selectOneBySql(QLBuilder builder) {
        // TODO Auto-generated method stub
        try {
            return (T) this.getOneBySql(builder);
        } catch (NoResultException ex) {
            return null;
        }
    }


    @SuppressWarnings("unchecked")
    public <T> List<T> selectManyByJpql(String jpql, Object...params) {
        // TODO Auto-generated method stub
        Query query = this.createQuery(jpql, params);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> selectManyByJpql(QLBuilder builder) {
        // TODO Auto-generated method stub
        if (builder.getMainClause() == null) {
            builder.setMainClause("SELECT obj FROM " + this.entityClass.getSimpleName() + " AS obj");
        }

        Query query = this.getEntityManager().createQuery(builder.getFullQL());

        Map<String, Object> params = builder.getParams();

        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> selectManyLimitByJpql(String jpql, Integer limit, Object...params) {
        // TODO Auto-generated method stub
        Query query = this.createQuery(jpql, params);

        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public <T> List<T> selectManyLimitByJpql(QLBuilder builder, Integer limit) {
        // TODO Auto-generated method stub
        if (builder.getMainClause() == null) {
            builder.setMainClause("SELECT obj FROM " + this.entityClass.getSimpleName() + " AS obj");
        }

        Query query = this.getEntityManager().createQuery(builder.getFullQL());

        Map<String, Object> params = builder.getParams();

        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        query.setMaxResults(limit);

        return query.getResultList();
    }


    @SuppressWarnings("unchecked")
    public <T> List<T> selectManyBySql(String sql, Object...params) {
        // TODO Auto-generated method stub
        Query query = this.createNativeQuery(sql, params);

        return query.getResultList();
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> selectManyBySql(QLBuilder builder) {
        // TODO Auto-generated method stub
        if (builder.getMainClause() == null) {
            builder.setMainClause("SELECT * FROM " + this.getEntityTableName());
        }

        Query query = this.getEntityManager().createNativeQuery(builder.getFullQL());

        Map<String, Object> params = builder.getParams();

        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        return query.getResultList();
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> Pagination<T> selectPagedByJpql(String jpql, Pagination<T> page, Object...params) {
        // TODO Auto-generated method stub
        Query countQuery = this.createQuery(this.getCountQL(jpql), params);
        page.setTotal(Long.parseLong(countQuery.getSingleResult().toString()));
        if (page.getTotal() > 0) {
            Query query = this.createQuery(jpql, params);
            query.setFirstResult(page.getStart());
            query.setMaxResults(page.getLimit());
            page.setItems(query.getResultList());
        }
        return page;
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> Pagination<T> selectPagedByJpql(QLBuilder builder, Pagination<T> page) {
        // TODO Auto-generated method stub
        if (builder.getMainClause() == null) {
            builder.setMainClause("SELECT obj FROM " + this.entityClass.getSimpleName() + " AS obj");
        }

        if (StringUtils.isNoneEmpty(page.getSort())) {
            String regex = "((?i)order)\\s+((?i)by)";

            Pattern p = Pattern.compile(regex);

            Matcher m = p.matcher(builder.getFullQL());

            String val = null;

            while (m.find()) {
                val = m.group();
                builder.add("," + page.getSort() + " " + page.getDir());
            }

            if (val == null) {
                builder.add(" ORDER BY " + page.getSort() + " " + page.getDir());
            }
        }

        Query query = this.getEntityManager().createQuery(builder.getFullQL());
        Query countQuery = this.getEntityManager().createQuery(this.getCountQL(builder.getFullQL()));

        Map<String, Object> params = builder.getParams();

        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        for (String key : params.keySet()) {
            countQuery.setParameter(key, params.get(key));
        }

        query.setFirstResult(page.getStart());
        query.setMaxResults(page.getLimit());

        page.setItems(query.getResultList());
        // page.setTotal((Long) countQuery.getSingleResult());
        page.setTotal(Long.parseLong(countQuery.getSingleResult().toString()));
        return page;
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> Pagination<T> selectPagedBySql(String sql, Pagination<T> page, Object...params) {
        // TODO Auto-generated method stub
        Query query = this.createNativeQuery(sql, params);

        Query countQuery = this.createNativeQuery(this.getCountSql(sql), params);

        query.setFirstResult(page.getStart());
        query.setMaxResults(page.getLimit());

        page.setItems(query.getResultList());
        // java.math.BigDecimal

        page.setTotal(Long.parseLong(countQuery.getSingleResult().toString()));

        // page.setTotal((Long) countQuery.getSingleResult());

        return page;
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> Pagination<T> selectPagedBySql(String sql, String resultSetMapping, Pagination<T> page, Object...params) {
        // TODO Auto-generated method stub
        Query query = this.createNativeQuery(sql, resultSetMapping, params);
        Query countQuery = this.createNativeQuery(this.getCountSql(sql), params);

        query.setFirstResult(page.getStart());
        query.setMaxResults(page.getLimit());

        page.setItems(query.getResultList());
        // page.setTotal((Long) countQuery.getSingleResult());
        page.setTotal(Long.parseLong(countQuery.getSingleResult().toString()));
        return page;
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> Pagination<T> selectPagedBySql(QLBuilder builder, Pagination<T> page) {
        // TODO Auto-generated method stub
        if (builder.getMainClause() == null) {
            builder.setMainClause("SELECT * FROM " + this.getEntityTableName());
        }

        if (StringUtils.isNoneEmpty(page.getSort())) {
            String regex = "((?i)order)\\s+((?i)by)";

            Pattern p = Pattern.compile(regex);

            Matcher m = p.matcher(builder.getFullQL());

            String val = null;

            while (m.find()) {
                val = m.group();
                builder.add("," + page.getSort() + " " + page.getDir());
            }

            if (val == null) {
                builder.add(" ORDER BY " + page.getSort() + " " + page.getDir());
            }
        }

        Query query = this.getEntityManager().createNativeQuery(builder.getFullQL());
        Query countQuery = this.getEntityManager().createNativeQuery(this.getCountSql(builder.getFullQL()));

        Map<String, Object> params = builder.getParams();

        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        for (String key : params.keySet()) {
            countQuery.setParameter(key, params.get(key));
        }

        query.setFirstResult(page.getStart());
        query.setMaxResults(page.getLimit());

        page.setItems(query.getResultList());
        // page.setTotal((Long) countQuery.getSingleResult());
        page.setTotal(Long.parseLong(countQuery.getSingleResult().toString()));
        return page;
    }

    protected Query createQuery(String jpql, Object...params) {
        Query query = this.getEntityManager().createQuery(jpql);

        QLBuilder builder = new QLBuilder();

        String[] paramNames = builder.resolveVarlible(jpql);

        if (paramNames != null && paramNames.length > 0) {
            if (params == null || paramNames.length != params.length) {
                throw new RuntimeException("参数个数和参数值个数不匹配");
            }

            for (int i = 0; i < paramNames.length; i++) {
                query.setParameter(paramNames[i], params[i]);
            }
        }

        return query;
    }

    protected Query createNativeQuery(String sql, Object...params) {
        Query query = this.getEntityManager().createNativeQuery(sql);

        QLBuilder builder = new QLBuilder();

        String[] paramNames = builder.resolveVarlible(sql);

        if (paramNames != null && paramNames.length > 0) {
            // if(params==null||paramNames.length!=params.length)
            if (params == null || paramNames.length > params.length) {
                // throw new RuntimeException("参数个数和参数值个数不匹配");
                throw new RuntimeException("未设定参数值");
            }

            for (int i = 0; i < paramNames.length; i++) {
                query.setParameter(paramNames[i], params[i]);
            }
        }

        return query;
    }

    protected Query createNativeQuery(String sql, String resultSetMapping, Object...params) {
        Query query = this.getEntityManager().createNativeQuery(sql, resultSetMapping);

        QLBuilder builder = new QLBuilder();

        String[] paramNames = builder.resolveVarlible(sql);

        if (paramNames != null && paramNames.length > 0) {
            /**
             * if(params==null||paramNames.length!=params.length) { throw new RuntimeException("参数个数和参数值个数不匹配"); }
             **/
            if (params == null || paramNames.length > params.length) {
                // throw new RuntimeException("参数个数和参数值个数不匹配");
                throw new RuntimeException("未设定参数值");
            }

            for (int i = 0; i < paramNames.length; i++) {
                query.setParameter(paramNames[i], params[i]);
            }
        }

        return query;
    }

    protected String getCountSql(String sql) {
        return "select count(*) from (" + sql + ")";
    }

    public String getCountQL(String ql) {
        String regex = "((?i)select)\\s{1}.*?(\\s{1}((?i)from))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(ql);
        while (m.find()) {
            String val = m.group();
            String mainClause = val.substring(6, val.length() - 5);
            mainClause = mainClause.trim();

            if (mainClause.startsWith("new ")) {
                mainClause.substring(mainClause.indexOf("(") + 1, mainClause.indexOf(")"));
            }

            String countQL = ql.replaceAll(regex, "SELECT COUNT(" + mainClause + ") FROM");
            // String countQL = ql.replaceAll(regex, "SELECT COUNT(t) FROM");
            // String countQL = ql.replaceAll("SELECT COUNT(" + mainClause + ") FROM", regex);

            return countQL;
        }

        return null;
    }

    protected String getEntityTableName() {
        Table table = this.entityClass.getAnnotation(Table.class);

        if (table != null) {
            return table.name();
        }

        return this.entityClass.getSimpleName();
    }
}