package org.ucla.sigma.daobase;

import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Criterion;

/**
 * Interface that defines common methods for Hibernate access.
 * 
 */
public interface IHibernateDAO {
	
	public Object findByKey(Class clase, Serializable clave);

	public List findByQuery(final String queryString, final String[] nombres, final Object[] values);
	
	public void flushSession() throws DAOException;

	public void clearSession() throws DAOException;

	public void update(Object persistentObject) throws DAOException;

	public void delete(Object persistentObject) throws DAOException;

	public List queryByExample(Object persistentObject)
			throws DAOException;

	public List findAll(Class clazz) throws DAOException;
	
	public List findAll(Class clazz, List orders) throws DAOException;

	public List findByProperty(Class clazz, Criterion restriction)
			throws DAOException;

	public List findByCriterions(Class clazz, List restrictions)
			throws DAOException;
	
	public List findByCriterions(Class clazz, List restrictions, List orders)
	throws DAOException; 

	public List findBySQLQuery(String sqlQuery, String aliasName, Class clazz)
			throws DAOException;
	
	public List findBySQLQuery(String sqlQuery)
	throws DAOException;

	public List findByHQLQuery(String hqlQuery) throws DAOException;

	public List findByNamedParam(String queryString, String paramName,
			Object value) throws DAOException;

	public List findByNamedParam(String queryString, String paramName,
			Object value, int firstResult, int maxResult) throws DAOException;

	public List findByNamedParam(final String queryString,
			final String[] paramNames, final Object[] values)
			throws DAOException;

	public List findByNamedParam(final String queryString,
			final String[] paramNames, final Object[] values,
			final int firstResult, final int maxResult) throws DAOException;

	public List findByValueBean(final String queryString, final Object valueBean)
			throws DAOException;

	public List findByNamedQuery(String queryName) throws DAOException;

	public List findByNamedQuery(String queryName, Object value)
			throws DAOException;

	public List findByNamedQuery(final String queryName, final Object[] values)
			throws DAOException;

	public abstract List findByNamedQueryAndNamedParam(String queryName,
			String paramName, Object value) throws DAOException;

	public List findByNamedQueryAndNamedParam(final String queryName,
			final String[] paramNames, final Object[] values)
			throws DAOException;

	public List findByNamedQueryAndValueBean(final String queryName,
			final Object valueBean) throws DAOException;

	public boolean isCacheQueries();

	public String getQueryCacheRegion();
	
	public Object get(Class clase, Serializable serializable);

	public Serializable save(Object persistentObject) throws DAOException;

	public void saveOrUpdate(Object persistentObject) throws DAOException;

	public List find(String queryString, int firstResult, int maxResult)
			throws DAOException;

	public List find(String queryString) throws DAOException;

	public List find(String queryString, Object value) throws DAOException;

	public List find(final String queryString, final Object[] values)
			throws DAOException;

	public List find(final String queryString, final Object[] values,
			final int firstResult, final int maxResult) throws DAOException;

	public List loadAll(final Class entityClass) throws DAOException;

	public List loadAll(final Class entityClass, final int firstResult,
			final int maxResult) throws DAOException;
	
	public void executeUpdateBySQL(String sql, Object[] valores) throws DAOException;

	public List findByCriterions(Class clazz, List restrictions, List orders,
			int firstResult, int maxResult) throws DAOException;
}