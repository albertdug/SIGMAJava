package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.EndoscopiaDAO;
import org.ucla.sigma.interfazservicio.IServicioEndoscopia;
import org.ucla.sigma.modelo.Endoscopia;

public class ServicioEndoscopia implements IServicioEndoscopia, Serializable {

	private EndoscopiaDAO endoscopiaDAO;

	public EndoscopiaDAO getEndoscopiaDAO() {
		return endoscopiaDAO;
	}

	public void setEndoscopiaDAO(EndoscopiaDAO endoscopiaDAO) {
		this.endoscopiaDAO = endoscopiaDAO;
	}

	@Override
	public void guardarEndoscopia(Endoscopia obj) {
		obj.setEstatus('A');
		endoscopiaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarEndoscopia(Endoscopia obj) {
		obj.setEstatus('E');
		endoscopiaDAO.saveOrUpdate(obj);
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
		return endoscopiaDAO.findByCriterions(Endoscopia.class, restricciones, orden);
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
		return endoscopiaDAO.findByCriterions(Endoscopia.class, restricciones, orden);
	}

	@Override
	public Endoscopia buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = endoscopiaDAO.findByCriterions(Endoscopia.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Endoscopia) busqueda.get(0);
		} else {
			return null;
		}
	}
}