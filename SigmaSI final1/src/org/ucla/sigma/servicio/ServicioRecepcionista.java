package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.RecepcionistaDAO;
import org.ucla.sigma.interfazservicio.IServicioRecepcionista;
import org.ucla.sigma.modelo.Recepcionista;

public class ServicioRecepcionista implements IServicioRecepcionista, Serializable {

	private RecepcionistaDAO recepcionistaDAO;

	public RecepcionistaDAO getRecepcionistaDAO() {
		return recepcionistaDAO;
	}

	public void setRecepcionistaDAO(RecepcionistaDAO recepcionistaDAO) {
		this.recepcionistaDAO = recepcionistaDAO;
	}
	
	@Override
	public void guardarRecepcionista(Recepcionista obj) {
		obj.setEstatus('A');
		recepcionistaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarRecepcionista(Recepcionista obj) {
		obj.setEstatus('E');
		recepcionistaDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioRecepcionista.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("cedula"));
		return recepcionistaDAO.findByCriterions(Recepcionista.class, restricciones, orden);
	}

	@Override
	public List buscarCoincidencias(int valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("nombre", valor));
		List orden = new ArrayList();
		orden.add(Order.asc("cedula"));
		return recepcionistaDAO.findByCriterions(Recepcionista.class, restricciones, orden);
	}

	@Override
	public Recepcionista buscarUno(int valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("cedula", valor));
		List busqueda = recepcionistaDAO.findByCriterions(Recepcionista.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Recepcionista) busqueda.get(0);
		} else {
			return null;
		}
	}

}
