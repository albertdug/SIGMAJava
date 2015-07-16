package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.PerfilDAO;
import org.ucla.sigma.interfazservicio.IServicioPerfil;
import org.ucla.sigma.modelo.Funcion;
import org.ucla.sigma.modelo.Perfil;

public class ServicioPerfil implements IServicioPerfil, Serializable {

	private PerfilDAO perfilDAO;

	public PerfilDAO getPerfilDAO() {
		return perfilDAO;
	}

	public void setPerfilDAO(PerfilDAO perfilDAO) {
		this.perfilDAO = perfilDAO;
	}

	@Override
	public void guardarPerfil(Perfil obj) {
		obj.setEstatus('A');
		perfilDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarPerfil(Perfil obj) {
		obj.setEstatus('E');
		perfilDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioPerfil.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return perfilDAO.findByCriterions(Perfil.class, restricciones, orden);
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
		return perfilDAO.findByCriterions(Perfil.class, restricciones, orden);
	}

	/**
	 * Busca por id
	 */
	@Override
	public Perfil buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = perfilDAO.findByCriterions(Perfil.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Perfil) busqueda.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Funcion> getFunciones(List<Perfil> perfiles) {
		List restricciones = new ArrayList();
		List tempfun = new ArrayList();
		for (Perfil perfil : perfiles) {
			for (Iterator iterator = perfil.getFuncions().iterator(); iterator
					.hasNext();) {
				Funcion object = (Funcion) iterator.next();
				tempfun.add(object.getId());

			}
		}

		restricciones.add(Restrictions.in("id", tempfun));

		List busqueda = perfilDAO.findByCriterions(Funcion.class,
				restricciones);
		return busqueda;
	}

}
