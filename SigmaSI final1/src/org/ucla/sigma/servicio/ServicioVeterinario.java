package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.VeterinarioDAO;
import org.ucla.sigma.interfazservicio.IServicioVeterinario;
import org.ucla.sigma.modelo.Veterinario;

public class ServicioVeterinario implements IServicioVeterinario, Serializable {

	private VeterinarioDAO veterinarioDAO;

	public VeterinarioDAO getVeterinarioDAO() {
		return veterinarioDAO;
	}

	public void setVeterinarioDAO(VeterinarioDAO veterinarioDAO) {
		this.veterinarioDAO = veterinarioDAO;
	}
	
	@Override
	public void guardarVeterinario(Veterinario obj) {
		obj.setEstatus('A');
		veterinarioDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarVeterinario(Veterinario obj) {
		obj.setEstatus('E');
		veterinarioDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioVeterinario.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("cedula"));
		return veterinarioDAO.findByCriterions(Veterinario.class, restricciones, orden);
	}

	@Override
	public List buscarCoincidencias(int valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("cedula", valor));
		List orden = new ArrayList();
		orden.add(Order.asc("cedula"));
		return veterinarioDAO.findByCriterions(Veterinario.class, restricciones, orden);
	}

	@Override
	public Veterinario buscarUno(int valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("cedula", valor));
		List busqueda = veterinarioDAO.findByCriterions(Veterinario.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Veterinario) busqueda.get(0);
		} else {
			return null;
		}
	}
	

}
