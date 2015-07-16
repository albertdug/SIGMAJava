package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.PatronDAO;
import org.ucla.sigma.interfazservicio.IServicioPatron;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.Patron;

public class ServicioPatron implements IServicioPatron, Serializable {

	private PatronDAO patronDAO;

	public PatronDAO getPatronDAO() {
		return patronDAO;
	}

	public void setPatronDAO(PatronDAO patronDAO) {
		this.patronDAO = patronDAO;
	}

	@Override
	public void guardarPatron(Patron obj) {
		obj.setEstatus('A');
		patronDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarPatron(Patron obj) {
		obj.setEstatus('E');
		patronDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioCiudad.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return patronDAO.findByCriterions(Patron.class, restricciones, orden);
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
		return patronDAO.findByCriterions(Patron.class, restricciones, orden);
	}

	@Override
	public Patron buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = patronDAO.findByCriterions(Patron.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Patron) busqueda.get(0);
		} else {
			return null;
		}
	}

}
