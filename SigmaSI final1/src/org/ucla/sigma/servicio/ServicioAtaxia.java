package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.AtaxiaDAO;
import org.ucla.sigma.interfazservicio.IServicioAtaxia;
import org.ucla.sigma.modelo.Ataxia;

public class ServicioAtaxia implements IServicioAtaxia, Serializable {

	private AtaxiaDAO ataxiaDAO;

	public AtaxiaDAO getAtaxiaDAO() {
		return ataxiaDAO;
	}

	public void setAtaxiaDAO(AtaxiaDAO ataxiaDAO) {
		this.ataxiaDAO = ataxiaDAO;
	}

	@Override
	public void guardarAtaxia(Ataxia obj) {
		obj.setEstatus('A');
		ataxiaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarAtaxia(Ataxia obj) {
		obj.setEstatus('E');
		ataxiaDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioAtaxia.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return ataxiaDAO.findByCriterions(Ataxia.class, restricciones, orden);
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
		return ataxiaDAO.findByCriterions(Ataxia.class, restricciones, orden);
	}

	@Override
	public Ataxia buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = ataxiaDAO.findByCriterions(Ataxia.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Ataxia) busqueda.get(0);
		} else {
			return null;
		}
	}

}
