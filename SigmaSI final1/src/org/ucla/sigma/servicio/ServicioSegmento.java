package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.SegmentoDAO;
import org.ucla.sigma.interfazservicio.IServicioSegmento;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Segmento;

public class ServicioSegmento implements IServicioSegmento, Serializable {

	private SegmentoDAO segmentoDAO;

	public SegmentoDAO getSegmentoDAO() {
		return segmentoDAO;
	}

	public void setSegmentoDAO(SegmentoDAO segmentoDAO) {
		this.segmentoDAO = segmentoDAO;
	}
	
	@Override
	public void guardarSegmento(Segmento obj) {
		obj.setEstatus('A');
		segmentoDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarSegmento(Segmento obj) {
		obj.setEstatus('E');
		segmentoDAO.saveOrUpdate(obj);
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
		return segmentoDAO.findByCriterions(Segmento.class, restricciones, orden);
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
		return segmentoDAO.findByCriterions(Segmento.class, restricciones, orden);
	}

	@Override
	public Segmento buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = segmentoDAO.findByCriterions(Segmento.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Segmento) busqueda.get(0);
		} else {
			return null;
		}
	}


}

