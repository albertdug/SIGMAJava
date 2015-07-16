package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.ReflejoDAO;
import org.ucla.sigma.interfazservicio.IServicioReflejo;
import org.ucla.sigma.modelo.Reflejo;

public class ServicioReflejo implements IServicioReflejo, Serializable {

	private ReflejoDAO reflejoDAO;

	public ReflejoDAO getReflejoDAO() {
		return reflejoDAO;
	}

	public void setReflejoDAO(ReflejoDAO reflejoDAO) {
		this.reflejoDAO = reflejoDAO;
	}

	@Override
	public void guardarReflejo(Reflejo obj) {
		obj.setEstatus('A');
		reflejoDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarReflejo(Reflejo obj) {
		obj.setEstatus('E');
		reflejoDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioEstado.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return reflejoDAO.findByCriterions(Reflejo.class, restricciones, orden);
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
		return reflejoDAO.findByCriterions(Reflejo.class, restricciones, orden);
	}

	@Override
	public Reflejo buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = reflejoDAO.findByCriterions(Reflejo.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Reflejo) busqueda.get(0);
		} else {
			return null;
		}
	}

}
