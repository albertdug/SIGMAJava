package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.UnasDAO;
import org.ucla.sigma.interfazservicio.IServicioUnas;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.Unas;

public class ServicioUnas implements IServicioUnas, Serializable {

	private UnasDAO unasDAO;

	public UnasDAO getUnasDAO() {
		return unasDAO;
	}

	public void setUnasDAO(UnasDAO unasDAO) {
		this.unasDAO = unasDAO;
	}

	@Override
	public void guardarUnas(Unas obj) {
		obj.setEstatus('A');
		unasDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarUnas(Unas obj) {
		obj.setEstatus('E');
		unasDAO.saveOrUpdate(obj);
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
		return unasDAO.findByCriterions(Unas.class, restricciones, orden);
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
		return unasDAO.findByCriterions(Unas.class, restricciones, orden);
	}

	@Override
	public Unas buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = unasDAO.findByCriterions(Unas.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Unas) busqueda.get(0);
		} else {
			return null;
		}
	}

}
