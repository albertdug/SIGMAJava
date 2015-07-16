package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.FrecuenciaBanoDAO;
import org.ucla.sigma.interfazservicio.IServicioFrecuenciaBano;
import org.ucla.sigma.modelo.FrecuenciaBano;

public class ServicioFrecuenciaBano implements IServicioFrecuenciaBano, Serializable {

	private FrecuenciaBanoDAO frecuenciaBanoDAO;

	public FrecuenciaBanoDAO getFrecuenciaBanoDAO() {
		return frecuenciaBanoDAO;
	}

	public void setFrecuenciaBanoDAO(FrecuenciaBanoDAO frecuenciaBanoDAO) {
		this.frecuenciaBanoDAO = frecuenciaBanoDAO;
	}
	
	@Override
	public void guardarFrecuenciaBano(FrecuenciaBano obj) {
		obj.setEstatus('A');
		frecuenciaBanoDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarFrecuenciaBano(FrecuenciaBano obj) {
		obj.setEstatus('E');
		frecuenciaBanoDAO.saveOrUpdate(obj);
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
		return frecuenciaBanoDAO.findByCriterions(FrecuenciaBano.class, restricciones, orden);
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
		return frecuenciaBanoDAO.findByCriterions(FrecuenciaBano.class, restricciones, orden);
	}

	@Override
	public FrecuenciaBano buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = frecuenciaBanoDAO.findByCriterions(FrecuenciaBano.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (FrecuenciaBano) busqueda.get(0);
		} else {
			return null;
		}
	}
	

}
