package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.modelo.TipoTratamiento;
import org.ucla.sigma.modelo.Tratamiento;

public class ServicioPatologia implements IServicioPatologia, Serializable {

	private PatologiaDAO patologiaDAO;

	public PatologiaDAO getPatologiaDAO() {
		return patologiaDAO;
	}

	public void setPatologiaDAO(PatologiaDAO patologiaDAO) {
		this.patologiaDAO = patologiaDAO;
	}
	
	@Override
	public void guardarPatologia(Patologia obj) {
		obj.setEstatus('A');
		patologiaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarPatologia(Patologia obj) {
		obj.setEstatus('E');
		patologiaDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioPatologia.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return patologiaDAO.findByCriterions(Patologia.class, restricciones, orden);
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
		return patologiaDAO.findByCriterions(Patologia.class, restricciones, orden);
	}

	@Override
	public Patologia buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = patologiaDAO.findByCriterions(Patologia.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Patologia) busqueda.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List buscarCoincidenciasTipo(TipoPatologia valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("tipoPatologia", valor));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return patologiaDAO.findByCriterions(Patologia.class, restricciones, orden);
	}
	
	@Override
	public List buscarCoincidenciasTipoVarias(Set<TipoPatologia> tipoPatologias, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.in("tipoPatologia", tipoPatologias));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return patologiaDAO.findByCriterions(Patologia.class, restricciones, orden);
	}
}
