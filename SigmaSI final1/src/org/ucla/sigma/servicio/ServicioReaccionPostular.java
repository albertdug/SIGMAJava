package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.ReaccionPostularDAO;
import org.ucla.sigma.interfazservicio.IServicioReaccionPostular;
import org.ucla.sigma.modelo.ReaccionPostular;

public class ServicioReaccionPostular implements IServicioReaccionPostular, Serializable {

	private ReaccionPostularDAO reaccionPostularDAO;

	public ReaccionPostularDAO getReaccionPostularDAO() {
		return reaccionPostularDAO;
	}

	public void setReaccionPostularDAO(ReaccionPostularDAO reaccionPostularDAO) {
		this.reaccionPostularDAO = reaccionPostularDAO;
	}

	@Override
	public void guardarReaccionPostular(ReaccionPostular obj) {
		obj.setEstatus('A');
		reaccionPostularDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarReaccionPostular(ReaccionPostular obj) {
		obj.setEstatus('E');
		reaccionPostularDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioReaccionPostular.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return reaccionPostularDAO.findByCriterions(ReaccionPostular.class, restricciones, orden);
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
		return reaccionPostularDAO.findByCriterions(ReaccionPostular.class, restricciones, orden);
	}

	@Override
	public ReaccionPostular buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = reaccionPostularDAO.findByCriterions(ReaccionPostular.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (ReaccionPostular) busqueda.get(0);
		} else {
			return null;
		}
	}

}
