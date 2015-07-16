package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.PersonaDAO;
import org.ucla.sigma.interfazservicio.IServicioPersona;
import org.ucla.sigma.modelo.Persona;

public class ServicioPersona implements IServicioPersona, Serializable {

	private PersonaDAO personaDAO;

	public PersonaDAO getPersonaDAO() {
		return personaDAO;
	}

	public void setPersonaDAO(PersonaDAO personaDAO) {
		this.personaDAO = personaDAO;
	}
	
	@Override
	public void guardarPersona(Persona obj) {
		obj.setEstatus('A');
		personaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarPersona(Persona obj) {
		obj.setEstatus('E');
		personaDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioPersona.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return personaDAO.findByCriterions(Persona.class, restricciones, orden);
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
		return personaDAO.findByCriterions(Persona.class, restricciones, orden);
	}

	@Override
	public Persona buscarUno(int valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("cedula", valor));
		List busqueda = personaDAO.findByCriterions(Persona.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Persona) busqueda.get(0);
		} else {
			return null;
		}
	}
		
}
