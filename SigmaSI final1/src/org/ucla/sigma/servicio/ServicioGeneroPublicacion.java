package org.ucla.sigma.servicio;

import java.io.Serializable;

import org.ucla.sigma.dao.GeneroPublicacionDAO;
import org.ucla.sigma.interfazservicio.IServicioGeneroPublicacion;
import org.ucla.sigma.modelo.GeneroPublicacion;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ServicioGeneroPublicacion implements IServicioGeneroPublicacion, Serializable {

	private GeneroPublicacionDAO generoPublicacionDAO;

	public GeneroPublicacionDAO getGeneroPublicacionDAO() {
		return generoPublicacionDAO;
	}

	public void setGeneroPublicacionDAO(GeneroPublicacionDAO generoPublicacionDAO) {
		this.generoPublicacionDAO = generoPublicacionDAO;
	}
	
	@Override
	public void guardarGeneroPublicacion(GeneroPublicacion obj) {
		obj.setEstatus('A');
		generoPublicacionDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarGeneroPublicacion(GeneroPublicacion obj) {
		obj.setEstatus('E');
		generoPublicacionDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioGeneroPublicacion.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return generoPublicacionDAO.findByCriterions(GeneroPublicacion.class, restricciones, orden);
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
		return generoPublicacionDAO.findByCriterions(GeneroPublicacion.class, restricciones, orden);
	}

	@Override
	public GeneroPublicacion buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = generoPublicacionDAO.findByCriterions(GeneroPublicacion.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (GeneroPublicacion) busqueda.get(0);
		} else {
			return null;
		}
	}
	
}
