package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.TipoServicioDAO;
import org.ucla.sigma.interfazservicio.IServicioTipoServicio;
import org.ucla.sigma.modelo.TipoServicio;

public class ServicioTipoServicio implements IServicioTipoServicio, Serializable {

	private TipoServicioDAO tipoServicioDAO;

	public TipoServicioDAO getTipoServicioDAO() {
		return tipoServicioDAO;
	}

	public void setTipoServicioDAO(TipoServicioDAO tipoServicioDAO) {
		this.tipoServicioDAO = tipoServicioDAO;
	}
	
	@Override
	public void guardarTipoServicio(TipoServicio obj) {
		obj.setEstatus('A');
		tipoServicioDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarTipoServicio(TipoServicio obj) {
		obj.setEstatus('E');
		tipoServicioDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            serviciotipoServicio.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return tipoServicioDAO.findByCriterions(TipoServicio.class, restricciones, orden);
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
		return tipoServicioDAO.findByCriterions(TipoServicio.class, restricciones, orden);
	}

	@Override
	public TipoServicio buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = tipoServicioDAO.findByCriterions(TipoServicio.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (TipoServicio) busqueda.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public TipoServicio buscarTipoServicio(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("id", valor));
		List busqueda = tipoServicioDAO.findByCriterions(TipoServicio.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (TipoServicio) busqueda.get(0);
		} else {
			return null;
		}
	}
	

}
