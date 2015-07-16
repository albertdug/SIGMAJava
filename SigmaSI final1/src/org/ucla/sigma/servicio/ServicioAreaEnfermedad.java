package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.AreaEnfermedadDAO;
import org.ucla.sigma.interfazservicio.IServicioAreaEnfermedad;
import org.ucla.sigma.modelo.AreaEnfermedad;

public class ServicioAreaEnfermedad implements IServicioAreaEnfermedad, Serializable {

	private AreaEnfermedadDAO areaenfermedadDAO;

	public AreaEnfermedadDAO getAreaEnfermedadDAO() {
		return areaenfermedadDAO;
	}

	public void setAreaEnfermedadDAO(AreaEnfermedadDAO areaenfermedadDAO) {
		this.areaenfermedadDAO = areaenfermedadDAO;
	}

	@Override
	public void guardarAreaEnfermedad(AreaEnfermedad obj) {
		obj.setEstatus('A');
		areaenfermedadDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarAreaEnfermedad(AreaEnfermedad obj) {
		obj.setEstatus('E');
		areaenfermedadDAO.saveOrUpdate(obj);
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
		return areaenfermedadDAO.findByCriterions(AreaEnfermedad.class, restricciones, orden);
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
		return areaenfermedadDAO.findByCriterions(AreaEnfermedad.class, restricciones, orden);
	}

	@Override
	public AreaEnfermedad buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = areaenfermedadDAO.findByCriterions(AreaEnfermedad.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (AreaEnfermedad) busqueda.get(0);
		} else {
			return null;
		}
	}

}
