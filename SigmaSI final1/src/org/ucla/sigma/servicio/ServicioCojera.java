package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.CojeraDAO;
import org.ucla.sigma.interfazservicio.IServicioCojera;
import org.ucla.sigma.modelo.Cojera;

public class ServicioCojera implements IServicioCojera, Serializable {

	private CojeraDAO cojeraDAO;

	public CojeraDAO getCojeraDAO() {
		return cojeraDAO;
	}

	public void setCojeraDAO(CojeraDAO cojeraDAO) {
		this.cojeraDAO = cojeraDAO;
	}

	@Override
	public void guardarCojera(Cojera obj) {
		obj.setEstatus('A');
		cojeraDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarCojera(Cojera obj) {
		obj.setEstatus('E');
		cojeraDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioCojera.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return cojeraDAO.findByCriterions(Cojera.class, restricciones, orden);
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
		return cojeraDAO.findByCriterions(Cojera.class, restricciones, orden);
	}

	@Override
	public Cojera buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = cojeraDAO.findByCriterions(Cojera.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Cojera) busqueda.get(0);
		} else {
			return null;
		}
	}

}
