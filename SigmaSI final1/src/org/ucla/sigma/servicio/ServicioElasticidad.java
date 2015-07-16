package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.ElasticidadDAO;
import org.ucla.sigma.interfazservicio.IServicioElasticidad;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.Elasticidad;

public class ServicioElasticidad implements IServicioElasticidad, Serializable {

	private ElasticidadDAO elasticidadDAO;

	public ElasticidadDAO getElasticidadDAO() {
		return elasticidadDAO;
	}

	public void setElasticidadDAO(ElasticidadDAO elasticidadDAO) {
		this.elasticidadDAO = elasticidadDAO;
	}

	@Override
	public void guardarElasticidad(Elasticidad obj) {
		obj.setEstatus('A');
		elasticidadDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarElasticidad(Elasticidad obj) {
		obj.setEstatus('E');
		elasticidadDAO.saveOrUpdate(obj);
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
		return elasticidadDAO.findByCriterions(Elasticidad.class, restricciones, orden);
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
		return elasticidadDAO.findByCriterions(Elasticidad.class, restricciones, orden);
	}

	@Override
	public Elasticidad buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = elasticidadDAO.findByCriterions(Elasticidad.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Elasticidad) busqueda.get(0);
		} else {
			return null;
		}
	}

}


