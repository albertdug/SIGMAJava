package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.PatronDistribucionDAO;
import org.ucla.sigma.interfazservicio.IServicioPatronDistribucion;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.PatronDistribucion;

public class ServicioPatronDistribucion implements IServicioPatronDistribucion, Serializable {

	private PatronDistribucionDAO patrondistribucionDAO;

	public PatronDistribucionDAO getPatronDistribucionDAO() {
		return patrondistribucionDAO;
	}

	public void setPatronDistribucionDAO(PatronDistribucionDAO patrondistribucionDAO) {
		this.patrondistribucionDAO = patrondistribucionDAO;
	}

	@Override
	public void guardarPatronDistribucion(PatronDistribucion obj) {
		obj.setEstatus('A');
		patrondistribucionDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarPatronDistribucion(PatronDistribucion obj) {
		obj.setEstatus('E');
		patrondistribucionDAO.saveOrUpdate(obj);
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
		return patrondistribucionDAO.findByCriterions(PatronDistribucion.class, restricciones, orden);
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
		return patrondistribucionDAO.findByCriterions(PatronDistribucion.class, restricciones, orden);
	}

	@Override
	public PatronDistribucion buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = patrondistribucionDAO.findByCriterions(PatronDistribucion.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (PatronDistribucion) busqueda.get(0);
		} else {
			return null;
		}
	}

}
