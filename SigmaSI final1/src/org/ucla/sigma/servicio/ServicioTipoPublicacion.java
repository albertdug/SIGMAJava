package org.ucla.sigma.servicio;

import java.io.Serializable;

import org.ucla.sigma.dao.TipoPublicacionDAO;
import org.ucla.sigma.interfazservicio.IServicioTipoPublicacion;
import org.ucla.sigma.modelo.TipoPublicacion;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ServicioTipoPublicacion implements IServicioTipoPublicacion, Serializable {

	private TipoPublicacionDAO tipoPublicacionDAO;

	public TipoPublicacionDAO getTipoPublicacionDAO() {
		return tipoPublicacionDAO;
	}

	public void setTipoPublicacionDAO(TipoPublicacionDAO tipoPublicacionDAO) {
		this.tipoPublicacionDAO = tipoPublicacionDAO;
	}
	
	@Override
	public void guardarTipoPublicacion(TipoPublicacion obj) {
		obj.setEstatus('A');
		tipoPublicacionDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarTipoPublicacion(TipoPublicacion obj) {
		obj.setEstatus('E');
		tipoPublicacionDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioTipoPublicacion.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return tipoPublicacionDAO.findByCriterions(TipoPublicacion.class, restricciones, orden);
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
		return tipoPublicacionDAO.findByCriterions(TipoPublicacion.class, restricciones, orden);
	}
	
	@Override
	public List buscarEn(List valores, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.in("id", valores));
		List orden = new ArrayList();
		return tipoPublicacionDAO.findByCriterions(TipoPublicacion.class, restricciones, orden);
	}

	@Override
	public TipoPublicacion buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = tipoPublicacionDAO.findByCriterions(TipoPublicacion.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (TipoPublicacion) busqueda.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public TipoPublicacion buscarUnoPorUri(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("uri", valor));
		List busqueda = tipoPublicacionDAO.findByCriterions(TipoPublicacion.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (TipoPublicacion) busqueda.get(0);
		} else {
			return null;
		}
	}

}
