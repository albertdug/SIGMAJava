package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.LesionSecundariaDAO;
import org.ucla.sigma.interfazservicio.IServicioLesionSecundaria;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.LesionSecundaria;

public class ServicioLesionSecundaria implements IServicioLesionSecundaria, Serializable {

	private LesionSecundariaDAO lesionsecundariaDAO;

	public LesionSecundariaDAO getLesionSecundariaDAO() {
		return lesionsecundariaDAO;
	}

	public void setLesionSecundariaDAO(LesionSecundariaDAO lesionsecundariaDAO) {
		this.lesionsecundariaDAO = lesionsecundariaDAO;
	}

	@Override
	public void guardarLesionSecundaria(LesionSecundaria obj) {
		obj.setEstatus('A');
		lesionsecundariaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarLesionSecundaria(LesionSecundaria obj) {
		obj.setEstatus('E');
		lesionsecundariaDAO.saveOrUpdate(obj);
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
		return lesionsecundariaDAO.findByCriterions(LesionSecundaria.class, restricciones, orden);
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
		return lesionsecundariaDAO.findByCriterions(LesionSecundaria.class, restricciones, orden);
	}

	@Override
	public LesionSecundaria buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = lesionsecundariaDAO.findByCriterions(LesionSecundaria.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (LesionSecundaria) busqueda.get(0);
		} else {
			return null;
		}
	}

}
