package org.kyll.myserver.base;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.base.paginated.Helper;
import org.kyll.myserver.base.paginated.Paginated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2014-10-28 11:31
 */
public class BaseHibernateDao<T extends Serializable, PK extends Serializable> implements BaseDao<T, PK> {
	/**
	 * 注入 HibernateTemplate
	 */
	@Autowired
	private HibernateTemplate hibernateTemplate;

	/**
	 * 实体类类型(由构造方法自动赋值)
	 */
	private Class<T> entityClass;

	/**
	 * 构造方法，根据实例类自动获取实体类类型
	 */
	public BaseHibernateDao() {
		this.entityClass = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Override
	public Query createQuery(String hql) {
		return getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
	}

	@Override
	public Query createSQLQuery(String sql) {
		return getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	}

	@Override
	public T get(PK id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public T getWithLock(PK id, LockMode lock) {
		T t = (T) getHibernateTemplate().get(entityClass, id, lock);
		if (t != null) {
			this.flush(); // 立即刷新，否则锁不会生效。
		}
		return t;
	}

	@Override
	public List<T> getAll() {
		return (List<T>) getHibernateTemplate().find("from " + entityClass.getSimpleName());
	}

	@Override
	public T load(PK id) {
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	@Override
	public T loadWithLock(PK id, LockMode lock) {
		T t = (T) getHibernateTemplate().load(entityClass, id, lock);
		if (t != null) {
			this.flush(); // 立即刷新，否则锁不会生效。
		}
		return t;
	}

	@Override
	public List<T> loadAll() {
		return (List<T>) getHibernateTemplate().loadAll(entityClass);
	}

	@Override
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	@Override
	public void updateWithLock(T entity, LockMode lock) {
		getHibernateTemplate().update(entity, lock);
		this.flush(); // 立即刷新，否则锁不会生效。
	}

	@Override
	public void save(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	@Override
	public void delete(PK... ids) {
		if (ids != null) {
			for (PK id : ids) {
				this.deleteByKey(id);
			}
		}
	}

	@Override
	public void delete(Collection<T> entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	@Override
	public void deleteWithLock(T entity, LockMode lock) {
		getHibernateTemplate().delete(entity, lock);
		this.flush(); // 立即刷新，否则锁不会生效。
	}

	@Override
	public void deleteByKey(PK id) {
		this.delete(this.get(id));
	}

	@Override
	public void deleteByKeyWithLock(PK id, LockMode lock) {
		this.deleteWithLock(this.load(id), lock);
	}

	@Override
	public int bulkUpdate(String hql) {
		return getHibernateTemplate().bulkUpdate(hql);
	}

	@Override
	public int bulkUpdate(String hql, Object[] values) {
		return getHibernateTemplate().bulkUpdate(hql, values);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql) {
		return (List<T>) getHibernateTemplate().find(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(StringBuilder hql) {
		return this.find(hql.toString());
	}

	@Override
	public List find(StringBuffer hql) {
		return this.find(hql.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Dataset<T> find(String hql, Paginated paginated) {
		String countHql = StringUtils.trim(hql);
		int position = StringUtils.indexOfIgnoreCase(countHql, "from");
		if (position == 0) {
			countHql = "select count(1) " + countHql;
		} else {
			String selectHql = StringUtils.substringBefore(countHql, "from");
			String fromHql = "from" + StringUtils.substringAfter(countHql, "from");
			if (StringUtils.containsIgnoreCase(selectHql, "distinct")) {
				countHql = "select count(distinct " + StringUtils.substringAfter(selectHql, "distinct") + ") " + fromHql;
			} else {
				countHql = "select count(1) " + fromHql;
			}
		}

		final String finalCountHql = countHql;
		int totalCount = getHibernateTemplate().execute(session -> ((Number) session.createQuery(finalCountHql).uniqueResult()).intValue());
		Dataset<T> dataset = Helper.makeDateset(paginated, totalCount, null);
		if (totalCount > 0) {
			dataset.setDataList(getHibernateTemplate().execute(session -> session.createQuery(hql).setFirstResult(paginated.getStartRecord()).setMaxResults(paginated.getMaxRecord()).list()));
		}
		return dataset;
	}

	@Override
	public Dataset<T> find(StringBuilder hql, Paginated paginated) {
		return this.find(hql.toString(), paginated);
	}

	@Override
	public Dataset<T> find(StringBuffer hql, Paginated paginated) {
		return this.find(hql.toString(), paginated);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findBySQL(String sql) {
		return getHibernateTemplate().execute(session -> session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql, Object[] values) {
		return (List<T>) getHibernateTemplate().find(hql, values);
	}

	@Override
	public Iterator iterate(String hql) {
		return getHibernateTemplate().iterate(hql);
	}

	@Override
	public Iterator iterate(String hql, Object[] values) {
		return getHibernateTemplate().iterate(hql, values);
	}

	@Override
	public void closeIterator(Iterator it) {
		getHibernateTemplate().closeIterator(it);
	}

	@Override
	public void lock(T entity, LockMode lock) {
		getHibernateTemplate().lock(entity, lock);
	}

	@Override
	public void flush() {
		getHibernateTemplate().flush();
	}
}
