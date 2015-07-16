package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.EspecieDAO;
import org.ucla.sigma.interfazservicio.IServicioEspecie;
import org.ucla.sigma.modelo.Especie;

public class ServicioEspecie implements IServicioEspecie, Serializable {

	private EspecieDAO especieDAO;

	public EspecieDAO getEspecieDAO() {
		return especieDAO;
	}

	public void setEspecieDAO(EspecieDAO especieDAO) {
		this.especieDAO = especieDAO;
	}

	@Override
	public void guardarEspecie(Especie obj) {
		obj.setEstatus('A');
		especieDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarEspecie(Especie obj) {
		obj.setEstatus('E');
		especieDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioEspecie.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return especieDAO.findByCriterions(Especie.class, restricciones, orden);
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
		return especieDAO.findByCriterions(Especie.class, restricciones, orden);
	}

	@Override
	public Especie buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = especieDAO.findByCriterions(Especie.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Especie) busqueda.get(0);
		} else {
			return null;
		}
	}

}
