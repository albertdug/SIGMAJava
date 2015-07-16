package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.TipoAlimentacionDAO;
import org.ucla.sigma.interfazservicio.IServicioTipoAlimentacion;
import org.ucla.sigma.modelo.TipoAlimentacion;


public class ServicioTipoAlimentacion implements IServicioTipoAlimentacion, Serializable {

	private TipoAlimentacionDAO tipoalimentacionDAO;

	public TipoAlimentacionDAO getTipoAlimentacionDAO() {
		return tipoalimentacionDAO;
	}

	public void setTipoAlimentacionDAO(TipoAlimentacionDAO tipoalimentacionDAO) {
		this.tipoalimentacionDAO = tipoalimentacionDAO;
	}

	@Override
	public void guardarTipoAlimentacion(TipoAlimentacion obj) {
		obj.setEstatus('A');
		tipoalimentacionDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarTipoAlimentacion(TipoAlimentacion obj) {
		obj.setEstatus('E');
		tipoalimentacionDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioEstado.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return tipoalimentacionDAO.findByCriterions(TipoAlimentacion.class, restricciones, orden);
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
		return tipoalimentacionDAO.findByCriterions(TipoAlimentacion.class, restricciones, orden);
	}

	@Override
	public TipoAlimentacion buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = tipoalimentacionDAO.findByCriterions(TipoAlimentacion.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (TipoAlimentacion) busqueda.get(0);
		} else {
			return null;
		}
	}
}
