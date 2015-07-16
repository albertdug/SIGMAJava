package org.ucla.sigma.daobase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author Edwar Valera Clase HibernateDAO: Realiza el mapping de los atributos
 *         de las clases modelos hacia las tablas de la base de datos
 */

public class HibernateDAO extends HibernateDaoSupport implements IHibernateDAO,
		Serializable {

	private boolean cacheQueries = false;

	private String queryCacheRegion;

	private Log log = LogFactory.getLog(HibernateDAO.class);

	/**
	 * Empty constructor
	 */
	public HibernateDAO() {
		super();
	}

	/**
	 * Metodo utilizado para guardar un registro
	 */
	@Override
	public Serializable save(Object persistentObject) throws DAOException {
		try {
			Serializable id = getHibernateTemplate().save(persistentObject);
			return id;
		} catch (HibernateException ex) {
			log.error("Fail to save persistentObject", ex);
			throw new DAOException("Fail to save persistentObject", ex);
		}
	}

	/**
	 * Metodo utilizado para guardar y actualizar un registroeste metodo
	 * actualiza automaticamente si el objeto ya existe de lo contrario guarda
	 * el registro
	 */
	@Override
	public void saveOrUpdate(Object persistentObject) throws DAOException {
		try {
			getHibernateTemplate().saveOrUpdate(persistentObject);
		} catch (HibernateException ex) {
			ex.printStackTrace();
			log.error("Fail to save or update persistentObject", ex);
			throw new DAOException("Fail to save or update persistentObject",
					ex);
		}
	}

	/**
	 * Metodo utilizado para actualizar un registro
	 */
	@Override
	public void update(Object persistentObject) throws DAOException {
		try {
			getHibernateTemplate().update(persistentObject);
		} catch (HibernateException ex) {
			log.error("Fail to update", ex);
			throw new DAOException("Fail to update", ex);
		}
	}

	/**
	 * Metodo utilizado para eliminar un registro
	 */
	@Override
	public void delete(Object persistentObject) throws DAOException {
		try {
			getHibernateTemplate().delete(persistentObject);
		} catch (HibernateException ex) {
			log.error("Fail to delete", ex);
			throw new DAOException("Fail to delete", ex);
		}
	}

	/**
	 * Metodo utilizado para buscar todos los objetos de una tableRetorna un
	 * objto del tipo lista
	 */
	@Override
	public List findAll(Class clazz) throws DAOException {
		List objs = new ArrayList();
		try {
			objs = getHibernateTemplate().loadAll(clazz);
		} catch (HibernateException ex) {
			log.error("Fail to find all  objects", ex);
			throw new DAOException("Fail to find all  objects", ex);
		}
		return objs;
	}

	/**
	 * Metodo utilizado para buscar todos los objetos de una tableRetorna un
	 * objto del tipo lista, acepta como paramento una lista de orden
	 * (ascendente o descendente)
	 */
	@Override
	public List findAll(Class clazz, List orders) throws DAOException {
		List objs = new ArrayList();
		Session session = this.getSession();
		try {
			Criteria criteria = session.createCriteria(clazz);
			for (Iterator iter = orders.iterator(); iter.hasNext();) {
				criteria.addOrder((Order) iter.next());
			}
			objs = criteria.list();
			session.close();
		} catch (HibernateException ex) {
			log.error("Fail to load all ", ex);
			throw new DAOException("Fail to load all", ex);
		}
		return objs;
	}

	/**
	 * Metodo utilizado para buscar todos los registros que coincidan con unas
	 * restriccionesRetorna una lista de todos los registros que coincidan
	 */
	@Override
	public List findByCriterions(Class clazz, List restrictions)
			throws DAOException {
		List objs = new ArrayList();

		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(clazz);
			Iterator it = restrictions.iterator();
			while (it.hasNext())
				criteria.add((Criterion) it.next());
			objs = criteria.list();
			session.close();
		} catch (HibernateException ex) {
			log.error("Fail to find objects by criterions", ex);
			throw new DAOException("Fail to find objects by criterions", ex);
		}
		return objs;
	}

	/**
	 * Metodo utilizado para buscar todos los registros que coincidan con unas
	 * restriccionesRetorna un objeto del tipo listaUsar una lista de order para
	 * especificar el orden de la lista, asc o desc
	 */
	@Override
	public List findByCriterions(Class clazz, List restrictions, List orders)
			throws DAOException {
		List objs = new ArrayList();

		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(clazz);
			Iterator it = restrictions.iterator();
			while (it.hasNext())
				criteria.add((Criterion) it.next());

			for (Iterator iter = orders.iterator(); iter.hasNext();) {
				criteria.addOrder((Order) iter.next());
			}
			objs = criteria.list();
			session.close();
		} catch (HibernateException ex) {
			log.error("Fail to find objects by criterions", ex);
			throw new DAOException("Fail to find objects by criterions", ex);
		}
		return objs;
	}

	
	/**
	 * Metodo utilizado para buscar todos los registros que coincidan con unas
	 * restriccionesRetorna un objeto del tipo listaUsar una lista de order para
	 * especificar el orden de la lista, asc o desc
	 */
	@Override
	public List findByCriterions(Class clazz, List restrictions, List orders,
			int firstResult, int maxResult)
			throws DAOException {
		List objs = new ArrayList();

		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(clazz)
					.setFirstResult(firstResult).setMaxResults(maxResult);
			Iterator it = restrictions.iterator();
			while (it.hasNext())
				criteria.add((Criterion) it.next());

			for (Iterator iter = orders.iterator(); iter.hasNext();) {
				criteria.addOrder((Order) iter.next());
			}
			objs = criteria.list();
			session.close();
		} catch (HibernateException ex) {
			log.error("Fail to find objects by criterions", ex);
			throw new DAOException("Fail to find objects by criterions", ex);
		}
		return objs;
	}
	
	/**
	 * Metodo utilizado para buscar registros que coincidan con unas
	 * restriccionesRetorna una lista de todos los registros que coincidanUsar
	 * una lista de order para especificar el orden de la lista, asc o descUsar
	 * los parmentos firstResult y maxResult para especificar el tamaño de la
	 * lista
	 */
	public List findByCriterions(Class clazz, List restrictions,
			int firstResult, int maxResult) throws DAOException {
		List objs = new ArrayList();

		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(clazz)
					.setFirstResult(firstResult).setMaxResults(maxResult);
			Iterator it = restrictions.iterator();
			while (it.hasNext())
				criteria.add((Criterion) it.next());
			objs = criteria.list();
			session.close();
		} catch (HibernateException ex) {
			log.error("Fail to find objects by criterions", ex);
			throw new DAOException("Fail to find objects by criterions", ex);
		}
		return objs;
	}

	@Override
	public Object findByKey(Class clase, Serializable clave) {
		try {
			Object data = getHibernateTemplate().get(clase, clave);
			if (data != null) {
				return data;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public void flushSession() throws DAOException {
		if (getSession() != null)
			try {
				getSession().flush();
			} catch (HibernateException e) {
				log.error("Fail to flush session", e);
				throw new DAOException("Fail to flush session", e);
			}
	}

	@Override
	public void clearSession() throws DAOException {
		try {
			getHibernateTemplate().clear();
		} catch (HibernateException ex) {
			log.error("Fail to clear session", ex);
			throw new DAOException("Fail to clear session", ex);
		}
	}

	@Override
	public List queryByExample(Object persistentObject) throws DAOException {
		List objs = new ArrayList();
		try {
			objs = getHibernateTemplate().findByExample(persistentObject);
		} catch (HibernateException ex) {
			log.error("Fail to find all  objects", ex);
			throw new DAOException("Fail to find all  objects", ex);
		}
		return objs;
	}

	@Override
	public void executeUpdateBySQL(String sql, Object[] valores)
			throws DAOException {

		try {
			getHibernateTemplate().bulkUpdate(sql, valores);
		} catch (Exception ex) {
			log.error("Fail to find all  objects", ex);
			throw new DAOException("Fail to find all  objects", ex);
		}
	}

	@Override
	public List findByProperty(Class clazz, Criterion restriction)
			throws DAOException {
		List objs = new ArrayList();
		try {
			Session session = getSession();
			objs = session.createCriteria(clazz).add(restriction).list();
			session.close();
		} catch (HibernateException ex) {
			log.error("Fail to find objects by property", ex);
			throw new DAOException("Fail to find objects by property", ex);
		}
		return objs;
	}

	@Override
	public List findBySQLQuery(String sqlQuery, String aliasName, Class clazz)
			throws DAOException {
		List result = new ArrayList();
		try {
			Session session = getSession();
			result = session.createSQLQuery(sqlQuery)
					.addEntity(aliasName, clazz).list();
			session.close();
		} catch (HibernateException ex) {
			log.error("Fail to execute query", ex);
			throw new DAOException("Fail to execute query", ex);
		}
		return result;
	}

	@Override
	public List findBySQLQuery(String sqlQuery) throws DAOException {
		List result = new ArrayList();
		try {
			Session session = getSession();
			result = session.createSQLQuery(sqlQuery).list();
			session.close();
		} catch (HibernateException ex) {
			log.error("Fail to execute query", ex);
			throw new DAOException("Fail to execute query", ex);
		}
		return result;
	}

	@Override
	public List findByHQLQuery(String hqlQuery) throws DAOException {
		List result = new ArrayList();
		try {
			Session session = getSession();
			result = session.createQuery(hqlQuery).list();
			session.close();
		} catch (HibernateException ex) {
			log.error("Fail to execute query", ex);
			throw new DAOException("Fail to execute query", ex);
		}
		return result;
	}

	@Override
	public List findByNamedParam(String queryString, String paramName,
			Object value) throws DAOException {
		return findByNamedParam(queryString, new String[] { paramName },
				new Object[] { value });
	}

	@Override
	public List findByNamedParam(String queryString, String paramName,
			Object value, int firstResult, int maxResult) throws DAOException {
		return findByNamedParam(queryString, new String[] { paramName },
				new Object[] { value }, firstResult, maxResult);
	}

	@Override
	public List findByQuery(final String queryString, final String[] nombres,
			final Object[] values) throws DAOException {
		try {

			Session session = getSession();
			Query queryObject = session.createQuery(queryString);

			prepareQuery(queryObject);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					if (values[i] instanceof String) {
						queryObject.setString(nombres[i], (String) values[i]);
					} else if (values[i] instanceof Integer) {
						queryObject.setInteger(nombres[i], (Integer) values[i]);
					} else if (values[i] instanceof Date) {
						queryObject.setDate(nombres[i], (Date) values[i]);
					} else {
						queryObject.setEntity(nombres[i], values[i]);
					}
				}
			}
			session.close();
			return queryObject.list();

		} catch (HibernateException ex) {
			log.error("Fail to find", ex);
			throw new DAOException("Fail to find", ex);
		}
	}

	@Override
	public List findByNamedParam(final String queryString,
			final String[] paramNames, final Object[] values)
			throws DAOException {
		try {
			if (paramNames.length != values.length) {
				throw new IllegalArgumentException(
						"Length of paramNames array must match length of values array");
			}
			Session session = getSession();
			Query queryObject = session.createQuery(queryString);

			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					applyNamedParameterToQuery(queryObject, paramNames[i],
							values[i]);
				}
			}
			session.close();
			return queryObject.list();

		} catch (HibernateException ex) {
			log.error("Fail to find", ex);
			throw new DAOException("Fail to find", ex);
		}
	}

	@Override
	public List findByNamedParam(final String queryString,
			final String[] paramNames, final Object[] values,
			final int firstResult, final int maxResult) throws DAOException {
		if (paramNames.length != values.length) {
			throw new IllegalArgumentException(
					"Length of paramNames array must match length of values array");
		}
		try {

			Session session = getSession();
			Query queryObject = session.createQuery(queryString);
			queryObject.setFirstResult(firstResult);
			queryObject.setMaxResults(maxResult);
			prepareQuery(queryObject);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					applyNamedParameterToQuery(queryObject, paramNames[i],
							values[i]);
				}
			}
			session.close();
			return queryObject.list();
		} catch (HibernateException ex) {
			log.error("Fail to find by named param");
			throw new DAOException("Fail to find by named param", ex);
		}
	}

	@Override
	public List findByValueBean(final String queryString, final Object valueBean)
			throws DAOException {
		Session session = getSession();
		try {
			Query queryObject = session.createQuery(queryString);
			prepareQuery(queryObject);
			queryObject.setProperties(valueBean);
			session.close();
			return queryObject.list();
		} catch (HibernateException ex) {
			log.error("Fail to find by valueBean", ex);
			throw new DAOException("Fail to find by valueBeam", ex);
		}
	}

	@Override
	public List findByNamedQuery(String queryName) throws DAOException {
		return findByNamedQuery(queryName, (Object[]) null);
	}

	@Override
	public List findByNamedQuery(String queryName, Object value)
			throws DAOException {
		return findByNamedQuery(queryName, new Object[] { value });
	}

	@Override
	public List findByNamedQuery(final String queryName, final Object[] values)
			throws DAOException {
		Session session = getSession();
		try {
			Query queryObject = session.getNamedQuery(queryName);
			prepareQuery(queryObject);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					queryObject.setParameter(i, values[i]);
				}
			}
			session.close();
			return queryObject.list();
		} catch (HibernateException ex) {
			log.error("Fail to find by Named query", ex);
			throw new DAOException("Fail to find by Named query", ex);
		}
	}

	@Override
	public List findByNamedQueryAndNamedParam(String queryName,
			String paramName, Object value) throws DAOException {
		return findByNamedQueryAndNamedParam(queryName,
				new String[] { paramName }, new Object[] { value });
	}

	@Override
	public List findByNamedQueryAndNamedParam(final String queryName,
			final String[] paramNames, final Object[] values)
			throws DAOException {
		if (paramNames != null && values != null
				&& paramNames.length != values.length) {
			throw new IllegalArgumentException(
					"Length of paramNames array must match length of values array");
		}
		Session session = getSession();
		try {
			Query queryObject = session.getNamedQuery(queryName);
			prepareQuery(queryObject);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					applyNamedParameterToQuery(queryObject, paramNames[i],
							values[i]);
				}
			}
			session.close();
			return queryObject.list();
		} catch (HibernateException ex) {
			log.error("Fail to find by Named query and Named Param", ex);
			throw new DAOException(
					"Fail to find by Named query and Named Param", ex);
		}
	}

	@Override
	public List findByNamedQueryAndValueBean(final String queryName,
			final Object valueBean) throws DAOException {
		Session session = getSession();
		try {
			Query queryObject = session.getNamedQuery(queryName);
			prepareQuery(queryObject);
			queryObject.setProperties(valueBean);
			session.close();
			return queryObject.list();
		} catch (HibernateException ex) {
			log.error("Fail to find by Named query and value bean", ex);
			throw new DAOException(
					"Fail to find by Named query and value bean", ex);
		}
	}

	protected void prepareQuery(Query queryObject) {
		if (isCacheQueries()) {
			queryObject.setCacheable(true);
			if (getQueryCacheRegion() != null) {
				queryObject.setCacheRegion(getQueryCacheRegion());
			}
		}
	}

	protected void applyNamedParameterToQuery(Query queryObject,
			String paramName, Object value) throws HibernateException {
		if (value instanceof Collection) {
			queryObject.setParameterList(paramName, (Collection) value);
		} else if (value instanceof Object[]) {
			queryObject.setParameterList(paramName, (Object[]) value);
		} else {
			queryObject.setParameter(paramName, value);
		}
	}

	@Override
	public boolean isCacheQueries() {
		return cacheQueries;
	}

	@Override
	public String getQueryCacheRegion() {
		return queryCacheRegion;
	}

	@Override
	public List find(String queryString, int firstResult, int maxResult)
			throws DAOException {
		return find(queryString, (Object[]) null, firstResult, maxResult);
	}

	@Override
	public List find(String queryString) throws DAOException {
		return find(queryString, (Object[]) null);
	}

	@Override
	public List find(String queryString, Object value) throws DAOException {
		return find(queryString, new Object[] { value });
	}

	@Override
	public List find(final String queryString, final Object[] values)
			throws DAOException {
		Session session = this.getSession();
		try {
			Query queryObject = session.createQuery(queryString);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					queryObject.setParameter(i, values[i]);
				}
			}
			session.close();
			return queryObject.list();
		} catch (HibernateException ex) {
			log.error("Fail to find by query string", ex);
			throw new DAOException("Fail to find by query string", ex);
		}
	}

	@Override
	public List find(final String queryString, final Object[] values,
			final int firstResult, final int maxResult) throws DAOException {
		Session session = this.getSession();
		try {
			Query queryObject = session.createQuery(queryString)
					.setFirstResult(firstResult).setMaxResults(maxResult);
			prepareQuery(queryObject);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					queryObject.setParameter(i, values[i]);
				}
			}
			session.close();
			return queryObject.list();
		} catch (HibernateException ex) {
			log.error("Fail to find by query string", ex);
			throw new DAOException("Fail to find by query string", ex);
		}
	}

	@Override
	public List loadAll(final Class entityClass) throws DAOException {
		try {
			return getHibernateTemplate().loadAll(entityClass);
		} catch (HibernateException ex) {
			log.error("Fail to load all ", ex);
			throw new DAOException("Fail to load all", ex);
		}
	}

	@Override
	public List loadAll(final Class entityClass, final int firstResult,
			final int maxResult) throws DAOException {
		Session session = this.getSession();
		try {
			Criteria criteria = session.createCriteria(entityClass)
					.setFirstResult(firstResult).setMaxResults(maxResult);
			session.close();
			return criteria.list();
		} catch (HibernateException ex) {
			log.error("Fail to load all ", ex);
			throw new DAOException("Fail to load all", ex);
		}
	}

	private Session openSession() {
		return getSession();
	}

	@Override
	public Object get(Class clase, Serializable valor) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		try {
			Object obj = session.get(clase, valor);
			session.close();
			return obj;
		} catch (HibernateException ex) {
			log.error("Fail to load all ", ex);
			throw new DAOException("Fail to load all", ex);
		}
	}
}