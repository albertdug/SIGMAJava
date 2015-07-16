package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.CiudadDAO;
import org.ucla.sigma.interfazservicio.IServicioCiudad;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.Estado;

public class ServicioCiudad implements IServicioCiudad, Serializable {

	private CiudadDAO ciudadDAO;

	public CiudadDAO getCiudadDAO() {
		return ciudadDAO;
	}

	public void setCiudadDAO(CiudadDAO ciudadDAO) {
		this.ciudadDAO = ciudadDAO;
	}

	@Override
	public void guardarCiudad(Ciudad obj) {
		obj.setEstatus('A');
		ciudadDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarCiudad(Ciudad obj) {
		obj.setEstatus('E');
		ciudadDAO.saveOrUpdate(obj);
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
		return ciudadDAO.findByCriterions(Ciudad.class, restricciones, orden);
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
		return ciudadDAO.findByCriterions(Ciudad.class, restricciones, orden);
	}

	@Override
	public Ciudad buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = ciudadDAO.findByCriterions(Ciudad.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Ciudad) busqueda.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public List buscarEstadosAsociados(Estado valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("estado", valor));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return ciudadDAO.findByCriterions(Ciudad.class, restricciones, orden);
	}

}