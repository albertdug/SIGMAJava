package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.LesionPrimariaDAO;
import org.ucla.sigma.interfazservicio.IServicioLesionPrimaria;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.LesionPrimaria;

public class ServicioLesionPrimaria implements IServicioLesionPrimaria, Serializable {

	private LesionPrimariaDAO lesionprimariaDAO;

	public LesionPrimariaDAO getLesionPrimariaDAO() {
		return lesionprimariaDAO;
	}

	public void setLesionPrimariaDAO(LesionPrimariaDAO lesionprimariaDAO) {
		this.lesionprimariaDAO = lesionprimariaDAO;
	}

	@Override
	public void guardarLesionPrimaria(LesionPrimaria obj) {
		obj.setEstatus('A');
		lesionprimariaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarLesionPrimaria(LesionPrimaria obj) {
		obj.setEstatus('E');
		lesionprimariaDAO.saveOrUpdate(obj);
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
		return lesionprimariaDAO.findByCriterions(LesionPrimaria.class, restricciones, orden);
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
		return lesionprimariaDAO.findByCriterions(LesionPrimaria.class, restricciones, orden);
	}

	@Override
	public LesionPrimaria buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = lesionprimariaDAO.findByCriterions(LesionPrimaria.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (LesionPrimaria) busqueda.get(0);
		} else {
			return null;
		}
	}

}
