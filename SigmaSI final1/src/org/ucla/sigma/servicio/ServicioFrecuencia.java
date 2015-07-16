package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.FrecuenciaDAO;
import org.ucla.sigma.interfazservicio.IServicioFrecuencia;
import org.ucla.sigma.modelo.Frecuencia;

public class ServicioFrecuencia implements IServicioFrecuencia, Serializable {

	private FrecuenciaDAO frecuenciaDAO;

	public FrecuenciaDAO getFrecuenciaDAO() {
		return frecuenciaDAO;
	}

	public void setFrecuenciaDAO(FrecuenciaDAO frecuenciaDAO) {
		this.frecuenciaDAO = frecuenciaDAO;
	}

	@Override
	public void guardarFrecuencia(Frecuencia obj) {
		obj.setEstatus('A');
		frecuenciaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarFrecuencia(Frecuencia obj) {
		obj.setEstatus('E');
		frecuenciaDAO.saveOrUpdate(obj);
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
		return frecuenciaDAO.findByCriterions(Frecuencia.class, restricciones, orden);
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
		return frecuenciaDAO.findByCriterions(Frecuencia.class, restricciones, orden);
	}

	@Override
	public Frecuencia buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = frecuenciaDAO.findByCriterions(Frecuencia.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Frecuencia) busqueda.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public List buscarFrecuenciaTipo(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("frecuenciaTipo.id", valor));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return frecuenciaDAO.findByCriterions(Frecuencia.class, restricciones, orden);
	}
	
	@Override
	public List buscarFrecuenciaAlimento(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("frecuenciaTipo.id", "FDA"));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return frecuenciaDAO.findByCriterions(Frecuencia.class, restricciones, orden);
	}
	
	@Override
	public List buscarFrecuenciaBano(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("frecuenciaTipo.id", "FDB"));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return frecuenciaDAO.findByCriterions(Frecuencia.class, restricciones, orden);
	}

	@Override
	public List buscarFrecuenciaRasca(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("frecuenciaTipo.id", "FDR"));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return frecuenciaDAO.findByCriterions(Frecuencia.class, restricciones, orden);
	}
	
}

