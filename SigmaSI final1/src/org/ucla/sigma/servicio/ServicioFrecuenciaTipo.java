package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.FrecuenciaTipoDAO;
import org.ucla.sigma.interfazservicio.IServicioFrecuenciaTipo;
import org.ucla.sigma.modelo.FrecuenciaTipo;

public class ServicioFrecuenciaTipo implements IServicioFrecuenciaTipo, Serializable {

	private FrecuenciaTipoDAO frecuenciaTipoDAO;

	public FrecuenciaTipoDAO getFrecuenciaTipoDAO() {
		return frecuenciaTipoDAO;
	}

	public void setFrecuenciaTipoDAO(FrecuenciaTipoDAO frecuenciaTipoDAO) {
		this.frecuenciaTipoDAO = frecuenciaTipoDAO;
	}
	
	@Override
	public void guardarFrecuenciaTipo(FrecuenciaTipo obj) {
		obj.setEstatus('A');
		frecuenciaTipoDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarFrecuenciaTipo(FrecuenciaTipo obj) {
		obj.setEstatus('E');
		frecuenciaTipoDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            serviciotipoServicio.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return frecuenciaTipoDAO.findByCriterions(FrecuenciaTipo.class, restricciones, orden);
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
		return frecuenciaTipoDAO.findByCriterions(FrecuenciaTipo.class, restricciones, orden);
	}

	@Override
	public FrecuenciaTipo buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = frecuenciaTipoDAO.findByCriterions(FrecuenciaTipo.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (FrecuenciaTipo) busqueda.get(0);
		} else {
			return null;
		}
	}
	

}
