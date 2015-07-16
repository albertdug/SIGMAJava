package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.ProfundidadDAO;
import org.ucla.sigma.interfazservicio.IServicioProfundidad;
import org.ucla.sigma.modelo.Profundidad;

public class ServicioProfundidad implements IServicioProfundidad, Serializable {

	private ProfundidadDAO profundidadDAO;

	public ProfundidadDAO getProfundidadDAO() {
		return profundidadDAO;
	}

	public void setProfundidadDAO(ProfundidadDAO profundidadDAO) {
		this.profundidadDAO = profundidadDAO;
	}

	@Override
	public void guardarProfundidad(Profundidad obj) {
		obj.setEstatus('A');
		profundidadDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarProfundidad(Profundidad obj) {
		obj.setEstatus('E');
		profundidadDAO.saveOrUpdate(obj);
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
		return profundidadDAO.findByCriterions(Profundidad.class, restricciones, orden);
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
		return profundidadDAO.findByCriterions(Profundidad.class, restricciones, orden);
	}

	@Override
	public Profundidad buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = profundidadDAO.findByCriterions(Profundidad.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Profundidad) busqueda.get(0);
		} else {
			return null;
		}
	}

}
