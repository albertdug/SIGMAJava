package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.GanglioDAO;
import org.ucla.sigma.interfazservicio.IServicioGanglio;
import org.ucla.sigma.modelo.Ganglio;

public class ServicioGanglio implements IServicioGanglio, Serializable {

	private GanglioDAO ganglioDAO;

	public GanglioDAO getGanglioDAO() {
		return ganglioDAO;
	}

	public void setGanglioDAO(GanglioDAO ganglioDAO) {
		this.ganglioDAO = ganglioDAO;
	}

	@Override
	public void guardarGanglio(Ganglio obj) {
		obj.setEstatus('A');
		ganglioDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarGanglio(Ganglio obj) {
		obj.setEstatus('E');
		ganglioDAO.saveOrUpdate(obj);
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
		return ganglioDAO.findByCriterions(Ganglio.class, restricciones, orden);
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
		return ganglioDAO.findByCriterions(Ganglio.class, restricciones, orden);
	}

	@Override
	public Ganglio buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = ganglioDAO.findByCriterions(Ganglio.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Ganglio) busqueda.get(0);
		} else {
			return null;
		}
	}

}
