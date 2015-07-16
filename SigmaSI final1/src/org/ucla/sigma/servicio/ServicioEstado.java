package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.EstadoDAO;
import org.ucla.sigma.interfazservicio.IServicioEstado;
import org.ucla.sigma.modelo.Estado;

public class ServicioEstado implements IServicioEstado, Serializable {

	private EstadoDAO estadoDAO;

	public EstadoDAO getEstadoDAO() {
		return estadoDAO;
	}

	public void setEstadoDAO(EstadoDAO estadoDAO) {
		this.estadoDAO = estadoDAO;
	}

	@Override
	public void guardarEstado(Estado obj) {
		obj.setEstatus('A');
		estadoDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarEstado(Estado obj) {
		obj.setEstatus('E');
		estadoDAO.saveOrUpdate(obj);
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
		return estadoDAO.findByCriterions(Estado.class, restricciones, orden);
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
		return estadoDAO.findByCriterions(Estado.class, restricciones, orden);
	}

	@Override
	public Estado buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = estadoDAO.findByCriterions(Estado.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Estado) busqueda.get(0);
		} else {
			return null;
		}
	}

}
