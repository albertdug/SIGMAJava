package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.PosturaDAO;
import org.ucla.sigma.interfazservicio.IServicioPostura;
import org.ucla.sigma.modelo.Postura;

public class ServicioPostura implements IServicioPostura, Serializable {

	private PosturaDAO posturaDAO;

	public PosturaDAO getPosturaDAO() {
		return posturaDAO;
	}

	public void setPosturaDAO(PosturaDAO posturaDAO) {
		this.posturaDAO = posturaDAO;
	}

	@Override
	public void guardarPostura(Postura obj) {
		obj.setEstatus('A');
		posturaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarPostura(Postura obj) {
		obj.setEstatus('E');
		posturaDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioPostura.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return posturaDAO.findByCriterions(Postura.class, restricciones, orden);
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
		return posturaDAO.findByCriterions(Postura.class, restricciones, orden);
	}

	@Override
	public Postura buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = posturaDAO.findByCriterions(Postura.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Postura) busqueda.get(0);
		} else {
			return null;
		}
	}

}
