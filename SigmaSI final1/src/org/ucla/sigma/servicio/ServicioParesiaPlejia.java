package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.ParesiaPlejiaDAO;
import org.ucla.sigma.interfazservicio.IServicioParesiaPlejia;
import org.ucla.sigma.modelo.ParesiaPlejia;

public class ServicioParesiaPlejia implements IServicioParesiaPlejia, Serializable {

	private ParesiaPlejiaDAO paresiaPlejiaDAO;

	public ParesiaPlejiaDAO getParesiaPlejiaDAO() {
		return paresiaPlejiaDAO;
	}

	public void setParesiaPlejiaDAO(ParesiaPlejiaDAO paresiaPlejiaDAO) {
		this.paresiaPlejiaDAO = paresiaPlejiaDAO;
	}

	@Override
	public void guardarParesiaPlejia(ParesiaPlejia obj) {
		obj.setEstatus('A');
		paresiaPlejiaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarParesiaPlejia(ParesiaPlejia obj) {
		obj.setEstatus('E');
		paresiaPlejiaDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioParesiaPlejia.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return paresiaPlejiaDAO.findByCriterions(ParesiaPlejia.class, restricciones, orden);
	}

	@Override
	public List buscarCoincidencias(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor,
				MatchMode.ANYWHERE));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return paresiaPlejiaDAO.findByCriterions(ParesiaPlejia.class, restricciones, orden);
	}

	@Override
	public ParesiaPlejia buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = paresiaPlejiaDAO.findByCriterions(ParesiaPlejia.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (ParesiaPlejia) busqueda.get(0);
		} else {
			return null;
		}
	}

}
