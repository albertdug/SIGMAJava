package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.ConsistenciaDAO;
import org.ucla.sigma.interfazservicio.IServicioConsistencia;
import org.ucla.sigma.modelo.Consistencia;

public class ServicioConsistencia implements IServicioConsistencia, Serializable {

	private ConsistenciaDAO consistenciaDAO;

	public ConsistenciaDAO getConsistenciaDAO() {
		return consistenciaDAO;
	}

	public void setConsistenciaDAO(ConsistenciaDAO consistenciaDAO) {
		this.consistenciaDAO = consistenciaDAO;
	}

	@Override
	public void guardarConsistencia(Consistencia obj) {
		obj.setEstatus('A');
		consistenciaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarConsistencia(Consistencia obj) {
		obj.setEstatus('E');
		consistenciaDAO.saveOrUpdate(obj);
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
		return consistenciaDAO.findByCriterions(Consistencia.class, restricciones, orden);
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
		return consistenciaDAO.findByCriterions(Consistencia.class, restricciones, orden);
	}

	@Override
	public Consistencia buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = consistenciaDAO.findByCriterions(Consistencia.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Consistencia) busqueda.get(0);
		} else {
			return null;
		}
	}

}
