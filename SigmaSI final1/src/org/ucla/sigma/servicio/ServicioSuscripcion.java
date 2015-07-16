package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.SuscripcionDAO;
import org.ucla.sigma.interfazservicio.IServicioSuscripcion;
import org.ucla.sigma.modelo.Imagen;
import org.ucla.sigma.modelo.Suscripcion;
import org.ucla.sigma.modelo.TipoPublicacion;

public class ServicioSuscripcion implements IServicioSuscripcion, Serializable {

	private SuscripcionDAO suscripcionDAO;

	public SuscripcionDAO getSuscripcionDAO() {
		return suscripcionDAO;
	}

	public void setSuscripcionDAO(SuscripcionDAO suscripcionDAO) {
		this.suscripcionDAO = suscripcionDAO;
	}
	@Override
	public void guardarSuscripcion(Suscripcion obj) {
		obj.setEstatus('A');
		suscripcionDAO.saveOrUpdate(obj);
	}
	@Override
	public void borrarSuscripcion(Suscripcion obj) {
		obj.setEstatus('E');
		suscripcionDAO.saveOrUpdate(obj);
	}
	
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return suscripcionDAO.findByCriterions(Suscripcion.class, restricciones, orden);
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
		return suscripcionDAO.findByCriterions(Suscripcion.class, restricciones, orden);
	}
	
	@Override
	public List buscarCoincidenciasFecha(Date fechainicio, Date fechafinal, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.between("creacion", fechainicio, fechafinal));
		List orden = new ArrayList();
		orden.add(Order.asc("creacion"));
		return suscripcionDAO.findByCriterions(Suscripcion.class, restricciones, orden);
	}

	@Override
	public Suscripcion buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = suscripcionDAO.findByCriterions(Suscripcion.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Suscripcion) busqueda.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public Suscripcion buscarUnoCorreo(String valor) {
		List restricciones = new ArrayList();		
		restricciones.add(Restrictions.eq("correo", valor));
		List busqueda = suscripcionDAO.findByCriterions(Suscripcion.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Suscripcion) busqueda.get(0);
		} else {
			return null;
		}
	}

}
