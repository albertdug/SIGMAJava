package org.ucla.sigma.dao;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.ucla.sigma.daobase.DAOException;
import org.ucla.sigma.daobase.HibernateDAO;
import org.ucla.sigma.interfazdao.INoModelDAO;

public class NoModelDAO extends HibernateDAO implements INoModelDAO {
	/**
	 * Ejemplo:
	 * http://forum.springsource.org/showthread.php?45665-Spring-Hibernate
	 * -and-executing-raw-sql; SQLQuery sqlQuery =
	 * session.createSQLQuery("SET FOREIGN_KEY_CHECKS=?");
	 * sqlQuery.setParameter(1, sqlState); sqlQuery.executeUpdate();
	 */
	@Override
	public int executeSQL(String sql) {
		try {
			Session session = getSession();
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			int retorno = sqlQuery.executeUpdate();
			session.close();
			return retorno;
		} catch (HibernateException e) {
			throw new DAOException("Error al ejecutar el sql", e);
		}
	}
}
