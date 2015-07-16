package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.AvanceEnfermedadDAO;
import org.ucla.sigma.interfazservicio.IServicioAvanceEnfermedad;
import org.ucla.sigma.modelo.AvanceEnfermedad;

public class ServicioAvanceEnfermedad implements IServicioAvanceEnfermedad, Serializable {

	private AvanceEnfermedadDAO avanceenfermedadDAO;

	public AvanceEnfermedadDAO getAvanceEnfermedadDAO() {
		return avanceenfermedadDAO;
	}

	public void setAvanceEnfermedadDAO(AvanceEnfermedadDAO avanceenfermedadDAO) {
		this.avanceenfermedadDAO = avanceenfermedadDAO;
	}

	@Override
	public void guardarAvanceEnfermedad(AvanceEnfermedad obj) {
		obj.setEstatus('A');
		avanceenfermedadDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarAvanceEnfermedad(AvanceEnfermedad obj) {
		obj.setEstatus('E');
		avanceenfermedadDAO.saveOrUpdate(obj);
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
		return avanceenfermedadDAO.findByCriterions(AvanceEnfermedad.class, restricciones, orden);
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
		return avanceenfermedadDAO.findByCriterions(AvanceEnfermedad.class, restricciones, orden);
	}

	@Override
	public AvanceEnfermedad buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = avanceenfermedadDAO.findByCriterions(AvanceEnfermedad.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (AvanceEnfermedad) busqueda.get(0);
		} else {
			return null;
		}
	}

}
