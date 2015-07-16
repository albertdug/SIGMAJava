package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.ResponsableDAO;
import org.ucla.sigma.interfazservicio.IServicioResponsable;
import org.ucla.sigma.modelo.Responsable;

public class ServicioResponsable implements IServicioResponsable, Serializable {

	private ResponsableDAO responsableDAO;

	public ResponsableDAO getResponsableDAO() {
		return responsableDAO;
	}

	public void setResponsableDAO(ResponsableDAO responsableDAO) {
		this.responsableDAO = responsableDAO;
	}
	
	@Override
	public void guardarResponsable(Responsable obj) {
		obj.setEstatus('A');
		responsableDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarResponsable(Responsable obj) {
		obj.setEstatus('E');
		responsableDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioResponsable.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("cedula"));
		return responsableDAO.findByCriterions(Responsable.class, restricciones, orden);
	}

	@Override
	public List buscarCoincidencias(int valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("cedula", valor));
		List orden = new ArrayList();
		orden.add(Order.asc("cedula"));
		return responsableDAO.findByCriterions(Responsable.class, restricciones, orden);
	}

	@Override
	public Responsable buscarUno(int valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("cedula", valor));
		List busqueda = responsableDAO.findByCriterions(Responsable.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Responsable) busqueda.get(0);
		} else {
			return null;
		}
	}
	

}
