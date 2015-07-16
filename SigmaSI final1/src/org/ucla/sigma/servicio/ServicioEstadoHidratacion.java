package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.EstadoHidratacionDAO;
import org.ucla.sigma.interfazservicio.IServicioEstadoHidratacion;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.EstadoHidratacion;

public class ServicioEstadoHidratacion implements IServicioEstadoHidratacion, Serializable {

	private EstadoHidratacionDAO estadohidratacionDAO;

	public EstadoHidratacionDAO getEstadoHidratacionDAO() {
		return estadohidratacionDAO;
	}

	public void setEstadoHidratacionDAO(EstadoHidratacionDAO estadohidratacionDAO) {
		this.estadohidratacionDAO = estadohidratacionDAO;
	}

	@Override
	public void guardarEstadoHidratacion(EstadoHidratacion obj) {
		obj.setEstatus('A');
		estadohidratacionDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarEstadoHidratacion(EstadoHidratacion obj) {
		obj.setEstatus('E');
		estadohidratacionDAO.saveOrUpdate(obj);
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
		return estadohidratacionDAO.findByCriterions(EstadoHidratacion.class, restricciones, orden);
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
		return estadohidratacionDAO.findByCriterions(EstadoHidratacion.class, restricciones, orden);
	}

	@Override
	public EstadoHidratacion buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = estadohidratacionDAO.findByCriterions(EstadoHidratacion.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (EstadoHidratacion) busqueda.get(0);
		} else {
			return null;
		}
	}

}
