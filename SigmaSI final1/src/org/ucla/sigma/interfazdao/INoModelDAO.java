package org.ucla.sigma.interfazdao;

import org.ucla.sigma.daobase.IHibernateDAO;

public interface INoModelDAO extends IHibernateDAO {
	public int executeSQL(String sql);
}
