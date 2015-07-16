package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.EntidadExternaDAO;
import org.ucla.sigma.dao.ImagenDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioEntidadExterna;
import org.ucla.sigma.interfazservicio.IServicioImagen;
import org.ucla.sigma.interfazservicio.IServicioPaciente;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.EntidadExterna;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.EntidadExterna;

public class ServicioEntidadExterna implements IServicioEntidadExterna, Serializable {

	private EntidadExternaDAO entidadExternaDAO;

	public void setEntidadExternaDAO(EntidadExternaDAO entidadExternaDAO) {
		this.entidadExternaDAO = entidadExternaDAO;
	}

	public EntidadExternaDAO getEntidadExternaDAO() {
		return entidadExternaDAO;
	}
	
	@Override
	public void guardarEntidadExterna(EntidadExterna obj) {
		obj.setEstatus('A');
		entidadExternaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarEntidadExterna(EntidadExterna obj) {
		obj.setEstatus('E');
		entidadExternaDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioEntidadExterna.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return entidadExternaDAO.findByCriterions(EntidadExterna.class, restricciones, orden);
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
		return entidadExternaDAO.findByCriterions(EntidadExterna.class, restricciones, orden);
	}

	@Override
	public EntidadExterna buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = entidadExternaDAO.findByCriterions(EntidadExterna.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (EntidadExterna) busqueda.get(0);
		} else {
			return null;
		}
	}


	

	
}
