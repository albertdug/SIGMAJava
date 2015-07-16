package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.components.HelperMath;
import org.ucla.sigma.dao.PublicidadDAO;
import org.ucla.sigma.interfazservicio.IServicioPublicidad;
import org.ucla.sigma.modelo.Publicidad;
import org.ucla.sigma.modelo.Publicidad;

public class ServicioPublicidad implements IServicioPublicidad, Serializable {

	private PublicidadDAO publicidadDAO;

	public PublicidadDAO getPublicidadDAO() {
		return publicidadDAO;
	}

	public void setPublicidadDAO(PublicidadDAO publicidadDAO) {
		this.publicidadDAO = publicidadDAO;
	}
	
	@Override
	public void guardarPublicidad(Publicidad obj) {
		obj.setEstatus('A');
		publicidadDAO.saveOrUpdate(obj);
	}

	@Override
	public Publicidad getRand(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}

		restricciones.add(Restrictions.eq("activo", true));
		restricciones.add(Restrictions.ge("expiracion", HelperDate.now()));
		List busqueda = publicidadDAO.findByCriterions(Publicidad.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Publicidad) busqueda.get(HelperMath.aleatorio(0,
					busqueda.size()-1));
		} else {
			return null;
		}
	}
	
	@Override
	public Publicidad buscarUnoPorId(int id, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("id", id));
		List busqueda = publicidadDAO.findByCriterions(Publicidad.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Publicidad) busqueda.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void borrarPublicidad(Publicidad obj) {
		obj.setEstatus('E');
		publicidadDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioPublicidad.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("titulo"));
		return publicidadDAO.findByCriterions(Publicidad.class, restricciones, orden);
	}

	@Override
	public List buscarCoincidencias(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("titulo", valor,
				MatchMode.ANYWHERE));
		List orden = new ArrayList();
		orden.add(Order.asc("titulo"));
		return publicidadDAO.findByCriterions(Publicidad.class, restricciones, orden);
	}

	@Override
	public Publicidad buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("titulo", valor));
		List busqueda = publicidadDAO.findByCriterions(Publicidad.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Publicidad) busqueda.get(0);
		} else {
			return null;
		}
	}

}
