package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.RadiografiaDAO;
import org.ucla.sigma.interfazservicio.IServicioRadiografia;
import org.ucla.sigma.modelo.Radiografia;

public class ServicioRadiografia implements IServicioRadiografia, Serializable {

	private RadiografiaDAO radiografiaDAO;

	public RadiografiaDAO getRadiografiaDAO() {
		return radiografiaDAO;
	}

	public void setRadiografiaDAO(RadiografiaDAO radiografiaDAO) {
		this.radiografiaDAO = radiografiaDAO;
	}

	@Override
	public void guardarRadiografia(Radiografia obj) {
		obj.setEstatus('A');
		radiografiaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarRadiografia(Radiografia obj) {
		obj.setEstatus('E');
		radiografiaDAO.saveOrUpdate(obj);
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
		return radiografiaDAO.findByCriterions(Radiografia.class, restricciones, orden);
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
		return radiografiaDAO.findByCriterions(Radiografia.class, restricciones, orden);
	}

	@Override
	public Radiografia buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = radiografiaDAO.findByCriterions(Radiografia.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Radiografia) busqueda.get(0);
		} else {
			return null;
		}
	}
}