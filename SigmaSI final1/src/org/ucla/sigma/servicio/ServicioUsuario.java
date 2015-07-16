package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.UsuarioDAO;
import org.ucla.sigma.interfazservicio.IServicioUsuario;
import org.ucla.sigma.modelo.Usuario;

public class ServicioUsuario implements IServicioUsuario, Serializable {

	private UsuarioDAO usuarioDAO;

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
	
	@Override
	public void guardarUsuario(Usuario obj) {
		obj.setEstatus('A');
		usuarioDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarUsuario(Usuario obj) {
		obj.setEstatus('E');
		usuarioDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioUsuario.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("cedula"));
		return usuarioDAO.findByCriterions(Usuario.class, restricciones, orden);
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
		return usuarioDAO.findByCriterions(Usuario.class, restricciones, orden);
	}

	/**
	 * Busca por id
	 */
	@Override
	public Usuario buscarUno(int valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("cedula", valor));
		List busqueda = usuarioDAO.findByCriterions(Usuario.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Usuario) busqueda.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public Usuario Credenciales(String login,String password, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("login", login));
		restricciones.add(Restrictions.eq("password", password));
		List busqueda = usuarioDAO.findByCriterions(Usuario.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Usuario) busqueda.get(0);
		} else {
			return null;
		}
	}
	

}
