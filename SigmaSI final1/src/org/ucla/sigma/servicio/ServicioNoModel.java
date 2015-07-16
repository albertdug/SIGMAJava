package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.List;

import org.ucla.sigma.dao.NoModelDAO;
import org.ucla.sigma.daobase.DAOException;
import org.ucla.sigma.interfazservicio.IServicioNoModel;
import org.ucla.sigma.modelo.TipoPublicacion;

public class ServicioNoModel implements IServicioNoModel, Serializable {

	private NoModelDAO noModelDAO;

	public NoModelDAO getNoModelDAO() {
		return noModelDAO;
	}

	public void setNoModelDAO(NoModelDAO noModelDAO) {
		this.noModelDAO = noModelDAO;
	}

	@Override
	public int ejecutarSQL(String sql) {
		try {
			return noModelDAO.executeSQL(sql);
		} catch (Exception e) {
			throw new DAOException("Error al ejecutar el sql", e);
		}
	}

	@Override
	public int count(String sql) {
		List list = noModelDAO.findBySQLQuery(sql);

		if (list.size() > 0) {
			return Integer.parseInt(list.get(0).toString());
		}

		return 0;
	}

}
