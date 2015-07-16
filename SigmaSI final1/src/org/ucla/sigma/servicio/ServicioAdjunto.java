package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.AdjuntoDAO;
import org.ucla.sigma.interfazservicio.IServicioAdjunto;
import org.ucla.sigma.modelo.Adjunto;

public class ServicioAdjunto implements IServicioAdjunto, Serializable {

	private AdjuntoDAO adjuntoDAO;

	public AdjuntoDAO getAdjuntoDAO() {
		return adjuntoDAO;
	}

	public void setAdjuntoDAO(AdjuntoDAO adjuntoDAO) {
		this.adjuntoDAO = adjuntoDAO;
	}
	
	@Override
	public void guardarAdjunto(Adjunto obj) {
		obj.setEstatus('A');
		adjuntoDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarAdjunto(Adjunto obj) {
		obj.setEstatus('E');
		adjuntoDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioAdjunto.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return adjuntoDAO.findByCriterions(Adjunto.class, restricciones, orden);
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
		return adjuntoDAO.findByCriterions(Adjunto.class, restricciones, orden);
	}

	@Override
	public Adjunto buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = adjuntoDAO.findByCriterions(Adjunto.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Adjunto) busqueda.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public Adjunto buscarUnoPorId(int id, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("id", id));
		List busqueda = adjuntoDAO.findByCriterions(Adjunto.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Adjunto) busqueda.get(0);
		} else {
			return null;
		}
	}
	
}
