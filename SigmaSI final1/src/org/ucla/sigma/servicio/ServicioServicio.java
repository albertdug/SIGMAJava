package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.ServicioDAO;
import org.ucla.sigma.interfazservicio.IServicioServicio;
import org.ucla.sigma.modelo.Raza;
import org.ucla.sigma.modelo.Servicio;
import org.ucla.sigma.modelo.TipoServicio;

public class ServicioServicio implements IServicioServicio, Serializable {

	private ServicioDAO servicioDAO;

	public ServicioDAO getServicioDAO() {
		return servicioDAO;
	}

	public void setServicioDAO(ServicioDAO servicioDAO) {
		this.servicioDAO = servicioDAO;
	}
	
	@Override
	public void guardarServicio(Servicio obj) {
		obj.setEstatus('A');
		servicioDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarServicio(Servicio obj) {
		obj.setEstatus('E');
		servicioDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioRaza.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return servicioDAO.findByCriterions(Servicio.class, restricciones, orden);
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
		return servicioDAO.findByCriterions(Servicio.class, restricciones, orden);
	}

	@Override
	public Servicio buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = servicioDAO.findByCriterions(Servicio.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Servicio) busqueda.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public List buscarCoincidenciasTipoServiciosVarias(TipoServicio valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("tipoServicio", valor));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return servicioDAO.findByCriterions(Servicio.class, restricciones, orden);
	}

}
