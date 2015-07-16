package org.ucla.sigma.servicio;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.TipoTratamiento;

import org.ucla.sigma.dao.TipoTratamientoDAO;
import org.ucla.sigma.interfazservicio.IServicioTipoTratamiento;

public class ServicioTipoTratamiento implements IServicioTipoTratamiento, Serializable {

	private TipoTratamientoDAO tipoTratamientoDAO;

	public TipoTratamientoDAO getTipoTratamientoDAO() {
		return tipoTratamientoDAO;
	}

	public void setTipoTratamientoDAO(TipoTratamientoDAO tipoTratamientoDAO) {
		this.tipoTratamientoDAO = tipoTratamientoDAO;
	}
	
	@Override
	public void guardarTipoTratamiento(TipoTratamiento obj) {
		obj.setEstatus('A');
		tipoTratamientoDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarTipoTratamiento(TipoTratamiento obj) {
		obj.setEstatus('E');
		tipoTratamientoDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioTipoTratamiento.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return tipoTratamientoDAO.findByCriterions(TipoTratamiento.class, restricciones, orden);
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
		return tipoTratamientoDAO.findByCriterions(TipoTratamiento.class, restricciones, orden);
	}

	@Override
	public TipoTratamiento buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = tipoTratamientoDAO.findByCriterions(TipoTratamiento.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (TipoTratamiento) busqueda.get(0);
		} else {
			return null;
		}
	}
}
