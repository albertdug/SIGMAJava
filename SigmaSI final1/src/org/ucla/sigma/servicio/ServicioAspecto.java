package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.AspectoDAO;
import org.ucla.sigma.interfazservicio.IServicioAspecto;
import org.ucla.sigma.modelo.Aspecto;

public class ServicioAspecto implements IServicioAspecto, Serializable {

	private AspectoDAO aspectoDAO;

	public AspectoDAO getAspectoDAO() {
		return aspectoDAO;
	}

	public void setAspectoDAO(AspectoDAO aspectoDAO) {
		this.aspectoDAO = aspectoDAO;
	}

	@Override
	public void guardarAspecto(Aspecto obj) {
		obj.setEstatus('A');
		aspectoDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarAspecto(Aspecto obj) {
		obj.setEstatus('E');
		aspectoDAO.saveOrUpdate(obj);
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
		return aspectoDAO.findByCriterions(Aspecto.class, restricciones, orden);
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
		return aspectoDAO.findByCriterions(Aspecto.class, restricciones, orden);
	}

	@Override
	public Aspecto buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = aspectoDAO.findByCriterions(Aspecto.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Aspecto) busqueda.get(0);
		} else {
			return null;
		}
	}

}
