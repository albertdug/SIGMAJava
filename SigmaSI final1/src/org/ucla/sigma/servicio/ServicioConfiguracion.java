package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.ConfiguracionDAO;
import org.ucla.sigma.interfazservicio.IServicioConfiguracion;
import org.ucla.sigma.modelo.Configuracion;

public class ServicioConfiguracion implements IServicioConfiguracion, Serializable {

	private ConfiguracionDAO configuracionDAO;

	public ConfiguracionDAO getConfiguracionDAO() {
		return configuracionDAO;
	}

	public void setConfiguracionDAO(ConfiguracionDAO configuracionDAO) {
		this.configuracionDAO = configuracionDAO;
	}

	@Override
	public void guardarConfiguracion(Configuracion obj) {
		obj.setEstatus('A');
		configuracionDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarConfiguracion(Configuracion obj) {
		obj.setEstatus('E');
		configuracionDAO.saveOrUpdate(obj);
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
		return configuracionDAO.findByCriterions(Configuracion.class, restricciones, orden);
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
		return configuracionDAO.findByCriterions(Configuracion.class, restricciones, orden);
	}

	@Override
	public Configuracion buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = configuracionDAO.findByCriterions(Configuracion.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Configuracion) busqueda.get(0);
		} else {
			return null;
		}
	}

}
