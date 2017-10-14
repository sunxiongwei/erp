package com.sun.framework.persistence;

import java.util.List;
import javax.persistence.EntityManager;
import com.sun.framework.persistence.entity.AbstractEntity;
import com.sun.framework.toolbox.Pagination;

public interface GenericDao<ENTITY, ID> {

    public EntityManager getEntityManager();
    
    AbstractEntity<?> save(AbstractEntity<?> entity);

    /**
     * 保存一个实体
     * 
     * @param entity 实体实例
     * @exception EntityExistsException 实体已存在
     * @exception IllegalArgumentException 不是一个实体类
     * @exception TransactionRequiredException 需要一个事物
     */
    public void insert(ENTITY entity);

    /**
     * 更新一个实例
     * 
     * @param entity 实体实例
     * @exception IllegalArgumentException 不是一个实体类
     * @exception TransactionRequiredException 需要一个事物
     */
    public ENTITY update(ENTITY entity);

    /**
     * 根据JPQL语句更新符合条件的对象
     * 
     * @param jpql JPQL语句
     * @param params 对应SQL语句中参数的值列表
     * @return 影响结果记录数
     */
    public int updateByJpql(String jpql, Object...params);

    /**
     * 根据Jpql去更新实体
     * 
     * @param builder
     * @return
     */
    public int updateByJpql(QLBuilder builder);

    /**
     * 根据SQL语句更新符合条件的对象
     * 
     * @param sql SQL语句
     * @param params 对应SQL语句中参数的值列表
     * @return 影响结果记录数
     */
    public int updateBySql(String sql, Object...params);

    /**
     * 根据Sql更新数据表
     * 
     * @param builder
     * @return
     */
    public int updateBySql(QLBuilder builder);

    /**
     * 根据实体主键删除实体记录
     * 
     * @param id 实体主键
     */
    public void delete(ID id);

    /**
     * 根据JPQL语句删除符合条件的值
     * 
     * @param jpql JPQL语句
     * @param params 对应JPQL语句中参数的值列表
     * @return 影响结果记录数
     */
    public int deleteByJpql(String jpql, Object...params);

    /**
     * 通过QL构建器来生成删除的JPQL语句来删除实体
     * 
     * @param builder
     * @return
     */
    public int deleteByJpql(QLBuilder builder);

    /**
     * 根据SQL语句删除符合条件的值
     * 
     * @param sql SQL语句
     * @param params 对应SQL语句中参数的值列表
     * @return 影响结果记录数
     */
    public int deleteBySql(String sql, Object...params);

    public int deleteBySql(QLBuilder builder);

    /**
     * 根据实体主键获取实体记录,如果实体记录不存在，在为判定是否为NULL的情况下使用会抛出EntityNotFoundException
     * 
     * @param id 实体主键
     * @return 实体实例
     * @exception EntityNotFoundException 实体不存在错误
     */
    public <T> T getOneById(ID id);

    /**
     * 根据JPQL语句搜索符合条件的唯一记录
     * 
     * @param jpql JPQL语句
     * @param params 对应JPQL语句中参数的值列表
     * @return 符合条件的结果记录
     * @exception NoResultException 没有符合条件的记录被找到错误
     * @exception NonUniqueResultException 符合条件的记录不唯一
     * @exception IllegalStateException 可能JPQL不是一句SELECT子句
     * @exception QueryTimeoutException 查询超时错误
     * @exception TransactionRequiredException 需要一个事物
     * @exception PessimisticLockException 悲观锁错误
     * @exception LockTimeoutException 锁超时错误
     * @exception PersistenceException 其它错误
     */
    public <T> T getOneByJpql(String jpql, Object...params);

    public <T> T getOneByJpql(QLBuilder builder);

    /**
     * 根据SQL语句搜索符合条件的唯一记录
     * 
     * @param sql SQL语句
     * @param params 对应SQL语句中参数的值列表
     * @return 符合条件的结果记录
     * @exception NoResultException 没有符合条件的记录被找到错误
     * @exception NonUniqueResultException 符合条件的记录不唯一
     * @exception IllegalStateException 可能JPQL不是一句SELECT子句
     * @exception QueryTimeoutException 查询超时错误
     * @exception TransactionRequiredException 需要一个事物
     * @exception PessimisticLockException 悲观锁错误
     * @exception LockTimeoutException 锁超时错误
     * @exception PersistenceException 其它错误
     */
    public <T> T getOneBySql(String sql, Object...params);

    /**
     * 根据QL构建器生成Sql生成SQL查询符合条件的唯一记录
     * 
     * @param builder
     * @return
     */
    public <T> T getOneBySql(QLBuilder builder);

    /**
     * 根据实体主键获取实体记录
     * 
     * @param id 实体主键
     * @return 实体实例
     */
    public <T> T selectOneById(ID id);

    /**
     * 根据JPQL语句搜索符合条件的唯一记录
     * 
     * @param jpql JPQL语句
     * @param params 对应JPQL语句中参数的值列表
     * @return 符合条件的结果记录
     * @exception NonUniqueResultException 符合条件的记录不唯一
     * @exception IllegalStateException 可能JPQL不是一句SELECT子句
     * @exception QueryTimeoutException 查询超时错误
     * @exception TransactionRequiredException 需要一个事物
     * @exception PessimisticLockException 悲观锁错误
     * @exception LockTimeoutException 锁超时错误
     * @exception PersistenceException 其它错误
     */
    public <T> T selectOneByJpql(String jpql, Object...params);

    public <T> T selectOneByJpql(QLBuilder builder);

    /**
     * 根据SQL语句搜索符合条件的唯一记录
     * 
     * @param sql SQL语句
     * @param params 对应SQL语句中参数的值列表
     * @return 符合条件的结果记录
     * @exception NonUniqueResultException 符合条件的记录不唯一
     * @exception IllegalStateException 可能JPQL不是一句SELECT子句
     * @exception QueryTimeoutException 查询超时错误
     * @exception TransactionRequiredException 需要一个事物
     * @exception PessimisticLockException 悲观锁错误
     * @exception LockTimeoutException 锁超时错误
     * @exception PersistenceException 其它错误
     */
    public <T> T selectOneBySql(String sql, Object...params);

    /**
     * 通过QL构建器生成SQL查询出符合条件的唯一记录
     * 
     * @param builder
     * @return
     */
    public <T> T selectOneBySql(QLBuilder builder);

    /**
     * 根据JPQL语句查询出所有符合记录的结果
     * 
     * @param jpql JPQL语句
     * @param params 对应JPQL语句中参数的值列表
     * @return 符合结果的记录数
     * @exception IllegalStateException 可能JPQL不是一句SELECT子句
     * @exception QueryTimeoutException 查询超时错误
     * @exception TransactionRequiredException 需要一个事物
     * @exception PessimisticLockException 悲观锁错误
     * @exception LockTimeoutException 锁超时错误
     * @exception PersistenceException 其它错误
     */
    public <T> List<T> selectManyByJpql(String jpql, Object...params);
    
    /**
     * 通过QL构建器
     * 
     * @param builder
     * @return
     */
    public <T> List<T> selectManyByJpql(QLBuilder builder);

    /**
     * 根据JPQL语句查询出所有符合记录的结果
     * 
     * @param jpql JPQL语句
     * @param limit 限制记录数
     * @param params 对应JPQL语句中参数的值列表
     * @return 符合结果的记录数
     * @exception IllegalStateException 可能JPQL不是一句SELECT子句
     * @exception QueryTimeoutException 查询超时错误
     * @exception TransactionRequiredException 需要一个事物
     * @exception PessimisticLockException 悲观锁错误
     * @exception LockTimeoutException 锁超时错误
     * @exception PersistenceException 其它错误
     */
    public <T> List<T> selectManyLimitByJpql(String jpql, Integer limit, Object...params);



    /**
     * 根据
     * 
     * @param builder
     * @param limit
     * @return
     */
    public <T> List<T> selectManyLimitByJpql(QLBuilder builder, Integer limit);

    /**
     * 根据SQL语句查询出所有符合记录的结果
     * 
     * @param sql SQL语句
     * @param params 对应SQL语句中参数的值列表
     * @return 符合结果的记录数
     * @exception IllegalStateException 可能SQL不是一句SELECT子句
     * @exception QueryTimeoutException 查询超时错误
     * @exception TransactionRequiredException 需要一个事物
     * @exception PessimisticLockException 悲观锁错误
     * @exception LockTimeoutException 锁超时错误
     * @exception PersistenceException 其它错误
     */
    public <T> List<T> selectManyBySql(String sql, Object...params);

    /**
     * 根据QL构建器生成SQL来查询符合条件的所有记录
     * 
     * @param builder
     * @return
     */
    public <T> List<T> selectManyBySql(QLBuilder builder);

    /**
     * 通过JPQL分页查询出符合条件的记录
     * 
     * @param jpql
     * @param page
     * @param params
     * @return
     */
    public <T> Pagination<T> selectPagedByJpql(String jpql, Pagination<T> page, Object...params);

    /**
     * 通过QL构建器生成JPQL分页查询出符合条件的记录
     * 
     * @param builder
     * @param page
     * @return
     */
    public <T> Pagination<T> selectPagedByJpql(QLBuilder builder, Pagination<T> page);

    /**
     * 通过SQL分页查询出符合条件的记录
     * 
     * @param sql
     * @param page
     * @param params
     * @return
     */
    public <T> Pagination<T> selectPagedBySql(String sql, Pagination<T> page, Object...params);

    /**
     * 根据Sql分页查询出符合条件的记录
     * 
     * @param sql
     * @param resultSetMapping
     * @param page
     * @param params
     * @return
     */
    public <T> Pagination<T> selectPagedBySql(String sql, String resultSetMapping, Pagination<T> page, Object...params);

    /**
     * 根据QL构建器生成SQL语句分页查询出符合条件的记录
     * 
     * @param builder
     * @param page
     * @return
     */
    public <T> Pagination<T> selectPagedBySql(QLBuilder builder, Pagination<T> page);
}
