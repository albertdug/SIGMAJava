package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.EcocardiogramaDAO;
import org.ucla.sigma.interfazservicio.IServicioEcocardiograma;
import org.ucla.sigma.modelo.Ecocardiograma;

public class ServicioEcocardiograma implements IServicioEcocardiograma, Serializable {

	private EcocardiogramaDAO ecocardiogramaDAO;

	public EcocardiogramaDAO getEcocardiogramaDAO() {
		return ecocardiogramaDAO;
	}

	public void setEcocardiogramaDAO(EcocardiogramaDAO ecocardiogramaDAO) {
		this.ecocardiogramaDAO = ecocardiogramaDAO;
	}

	@Override
	public void guardarEcocardiograma(Ecocardiograma obj) {
		obj.setEstatus('A');
		ecocardiogramaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarEcocardiograma(Ecocardiograma obj) {
		obj.setEstatus('E');
		ecocardiogramaDAO.saveOrUpdate(obj);
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
		return ecocardiogramaDAO.findByCriterions(Ecocardiograma.class, restricciones, orden);
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
		return ecocardiogramaDAO.findByCriterions(Ecocardiograma.class, restricciones, orden);
	}

	@Override
	public Ecocardiograma buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = ecocardiogramaDAO.findByCriterions(Ecocardiograma.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Ecocardiograma) busqueda.get(0);
		} else {
			return null;
		}
	}
}