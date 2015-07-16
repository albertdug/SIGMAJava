package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.OftalmologiaDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioOftalmologia;
import org.ucla.sigma.interfazservicio.IServicioPaciente;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.Oftalmologia;
import org.ucla.sigma.modelo.Oftalmologia;

public class ServicioOftalmologia implements IServicioOftalmologia, Serializable {

	private OftalmologiaDAO oftalmologiaDAO;

	public OftalmologiaDAO getOftalmologiaDAO() {
		return oftalmologiaDAO;
	}

	public void setOftalmologiaDAO(OftalmologiaDAO oftalmologiaDAO) {
		this.oftalmologiaDAO = oftalmologiaDAO;
	}
	
	@Override
	public void guardarOftalmologia(Oftalmologia obj) {
		obj.setEstatus('A');
		oftalmologiaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarOftalmologia(Oftalmologia obj) {
		obj.setEstatus('E');
		oftalmologiaDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioOftalmologia.buscarTodos();
	 */

	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return oftalmologiaDAO.findByCriterions(Oftalmologia.class, restricciones, orden);
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
		return oftalmologiaDAO.findByCriterions(Oftalmologia.class, restricciones, orden);
	}

	@Override
	public Oftalmologia buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = oftalmologiaDAO.findByCriterions(Oftalmologia.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Oftalmologia) busqueda.get(0);
		} else {
			return null;
		}
	}

	

	
}
