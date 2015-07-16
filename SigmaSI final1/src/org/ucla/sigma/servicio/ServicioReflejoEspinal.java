package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.ReflejoEspinalDAO;
import org.ucla.sigma.interfazservicio.IServicioReflejoEspinal;
import org.ucla.sigma.modelo.ReflejoEspinal;

public class ServicioReflejoEspinal implements IServicioReflejoEspinal, Serializable {

	private ReflejoEspinalDAO reflejoEspinalDAO;

	public ReflejoEspinalDAO getReflejoEspinalDAO() {
		return reflejoEspinalDAO;
	}

	public void setReflejoEspinalDAO(ReflejoEspinalDAO reflejoEspinalDAO) {
		this.reflejoEspinalDAO = reflejoEspinalDAO;
	}

	@Override
	public void guardarReflejoEspinal(ReflejoEspinal obj) {
		obj.setEstatus('A');
		reflejoEspinalDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarReflejoEspinal(ReflejoEspinal obj) {
		obj.setEstatus('E');
		reflejoEspinalDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioReflejoEspinal.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return reflejoEspinalDAO.findByCriterions(ReflejoEspinal.class, restricciones, orden);
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
		return reflejoEspinalDAO.findByCriterions(ReflejoEspinal.class, restricciones, orden);
	}

	@Override
	public ReflejoEspinal buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = reflejoEspinalDAO.findByCriterions(ReflejoEspinal.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (ReflejoEspinal) busqueda.get(0);
		} else {
			return null;
		}
	}

}
