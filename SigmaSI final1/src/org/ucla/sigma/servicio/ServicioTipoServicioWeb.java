package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.TipoServicioWebDAO;
import org.ucla.sigma.interfazservicio.IServicioTipoServicioWeb;
import org.ucla.sigma.modelo.TipoServicio;
import org.ucla.sigma.modelo.TipoServicioWeb;

public class ServicioTipoServicioWeb implements IServicioTipoServicioWeb, Serializable {

	private TipoServicioWebDAO tipoServicioWebDAO;

	public TipoServicioWebDAO getTipoServicioWebDAO() {
		return tipoServicioWebDAO;
	}

	public void setTipoServicioWebDAO(TipoServicioWebDAO tipoServicioWebDAO) {
		this.tipoServicioWebDAO = tipoServicioWebDAO;
	}
	
	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            serviciotipoServicioWeb.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("id"));
		return tipoServicioWebDAO.findByCriterions(TipoServicioWeb.class, restricciones, orden);
	}
	
	@Override
	public TipoServicioWeb buscarUnoPorUri(String uri, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("uri", uri));
		List busqueda = tipoServicioWebDAO.findByCriterions(TipoServicioWeb.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (TipoServicioWeb) busqueda.get(0);
		} else {
			return null;
		}
	}

}
