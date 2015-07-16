package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.ColorDAO;
import org.ucla.sigma.interfazservicio.IServicioColor;
import org.ucla.sigma.modelo.Color;

public class ServicioColor implements IServicioColor, Serializable {

	private ColorDAO colorDAO;

	public ColorDAO getColorDAO() {
		return colorDAO;
	}

	public void setColorDAO(ColorDAO colorDAO) {
		this.colorDAO = colorDAO;
	}

	@Override
	public void guardarColor(Color obj) {
		obj.setEstatus('A');
		colorDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarColor(Color obj) {
		obj.setEstatus('E');
		colorDAO.saveOrUpdate(obj);
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
		return colorDAO.findByCriterions(Color.class, restricciones, orden);
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
		return colorDAO.findByCriterions(Color.class, restricciones, orden);
	}

	@Override
	public Color buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = colorDAO.findByCriterions(Color.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Color) busqueda.get(0);
		} else {
			return null;
		}
	}

}
