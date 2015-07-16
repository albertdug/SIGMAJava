package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.NotificacionDAO;
import org.ucla.sigma.interfazservicio.IServicioNotificacion;
import org.ucla.sigma.modelo.Imagen;
import org.ucla.sigma.modelo.Notificacion;


public class ServicioNotificacion implements IServicioNotificacion, Serializable {

	private NotificacionDAO notificacionDAO;

	public NotificacionDAO getNotificacionDAO() {
		return notificacionDAO;
	}

	public void setNotificacionDAO(NotificacionDAO notificacionDAO) {
		this.notificacionDAO = notificacionDAO;
	}
	
	@Override
	public void guardarNotificacion(Notificacion obj) {
		obj.setEstatus('A');
		notificacionDAO.saveOrUpdate(obj);
	}
	
	@Override
	public void notificacionLeida(Notificacion obj) {
		obj.setEstatus('E');
		notificacionDAO.saveOrUpdate(obj);
	}
	
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.desc("creacion"));
		return notificacionDAO.findByCriterions(Notificacion.class, restricciones, orden);
	}
	
	@Override
	public List buscarCoincidenciasFechas(Date fechainicio, Date fechafinal, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.between("creacion", fechainicio, fechafinal));
		List orden = new ArrayList();
		orden.add(Order.desc("creacion"));
		return notificacionDAO.findByCriterions(Notificacion.class, restricciones, orden);
	}

	@Override
	public Notificacion buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = notificacionDAO.findByCriterions(Notificacion.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Notificacion) busqueda.get(0);
		} else {
			return null;
		}
	}

}

