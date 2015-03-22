package org.kyll.myserver.base;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.base.paginated.Paginated;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2014-10-27 16:41
 */
public interface BaseDao<T extends Serializable, PK extends Serializable> {
	Query createQuery(String hql);

	Query createSQLQuery(String sql);

	/**
	 * 根据主键获取实体。如果没有相应的实体，返回 null。
	 * @param id 主键
	 * @return 实体
	 */
	public T get(PK id);

	/**
	 * 根据主键获取实体并加锁。如果没有相应的实体，返回 null。
	 * @param id 主键
	 * @param lock 锁模式
	 * @return 实体
	 */
	public T getWithLock(PK id, LockMode lock);

	/**
	 * 获取全部实体。
	 * @return 实体 LIST
	 */
	public List<T> getAll();

	/**
	 * 根据主键获取实体。如果没有相应的实体，抛出异常。
	 * @param id 主键
	 * @return 实体
	 */
	public T load(PK id);

	/**
	 * 根据主键获取实体并加锁。如果没有相应的实体，抛出异常。
	 * @param id 主键
	 * @param lock 锁模式
	 * @return 实体
	 */
	public T loadWithLock(PK id, LockMode lock);

	/**
	 * 获取全部实体。
	 * @return 实体 LIST
	 */
	public List<T> loadAll();

	/**
	 * 更新实体
	 * @param entity 实体
	 */
	public void update(T entity);

	/**
	 * 更新实体并加锁
	 * @param entity 实体
	 * @param lock 锁模式
	 */
	public void updateWithLock(T entity, LockMode lock);

	/**
	 * 存储实体到数据库
	 * @param entity 实体
	 */
	public void save(T entity);

	/**
	 * 增加或更新实体
	 * @param entity 实体
	 */
	public void saveOrUpdate(T entity);

	/**
	 * 删除指定的实体
	 * @param entity 实体
	 */
	public void delete(T entity);

	/**
	 * 删除指定的实体
	 * @param ids 主键
	 */
	public void delete(PK... ids);

	/**
	 * 删除指定的实体
	 * @param entities 实体
	 */
	public void delete(Collection<T> entities);

	/**
	 * 加锁并删除指定的实体
	 * @param entity 实体
	 * @param lock 锁模式
	 */
	public void deleteWithLock(T entity, LockMode lock);

	//

	/**
	 * 根据主键删除指定实体
	 * @param id 主键
	 */
	public void deleteByKey(PK id);

	/**
	 * 根据主键加锁并删除指定的实体
	 * @param id 主键
	 * @param lock 锁模式
	 */
	public void deleteByKeyWithLock(PK id, LockMode lock);

	/**
	 * 使用HQL语句直接增加、更新、删除实体
	 * @param hql HQL
	 * @return 执行结果
	 */
	public int bulkUpdate(String hql);

	/**
	 * 使用带参数的HQL语句增加、更新、删除实体
	 * @param hql HQL
	 * @param values 参数
	 * @return 执行结果
	 */
	public int bulkUpdate(String hql, Object[] values);

	/**
	 * 使用HQL语句检索数据
	 * @param hql HQL
	 * @return 实体 LIST
	 */
	public List find(String hql);

	public List find(StringBuilder hql);

	public List find(StringBuffer hql);

	public Dataset<T> find(String hql, Paginated paginated);

	public Dataset<T> find(StringBuilder hql, Paginated paginated);

	public Dataset<T> find(StringBuffer hql, Paginated paginated);

	public List<Map<String, Object>> findBySQL(String sql);

	/**
	 * 使用带参数的HQL语句检索数据
	 * @param hql HQL
	 * @param values 参数
	 * @return 实体 LIST
	 */
	public List find(String hql, Object[] values);

	/**
	 * 使用HQL语句检索数据
	 * @param hql HQL
	 * @return 实体 Iterator
	 */
	public Iterator iterate(String hql);

	/**
	 * 使用带参数HQL语句检索数据
	 * @param hql HQL
	 * @param values 参数
	 * @return 实体 Iterator
	 */
	public Iterator iterate(String hql, Object[] values);

	/**
	 * 关闭检索返回的 Iterator
	 * @param it Iterator
	 */
	public void closeIterator(Iterator it);

	/**
	 * 加锁指定的实体
	 * @param entity 实体
	 * @param lockMode 锁模式
	 */
	public void lock(T entity, LockMode lockMode);

	/**
	 * 强制立即更新缓冲数据到数据库（否则仅在事务提交时才更新）
	 */
	public void flush();

}
