package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.FuncionDAO;
import org.ucla.sigma.interfazservicio.IServicioFuncion;
import org.ucla.sigma.modelo.Funcion;
import org.ucla.sigma.modelo.Perfil;

public class ServicioFuncion implements IServicioFuncion,
		Serializable {

	private FuncionDAO funcionDAO;

	public FuncionDAO getFuncionDAO() {
		return funcionDAO;
	}

	public void setFuncionDAO(FuncionDAO funcionDAO) {
		this.funcionDAO = funcionDAO;
	}

	@Override
	public void guardarFuncion(Funcion obj) {
		obj.setEstatus('A');
		funcionDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarFuncion(Funcion obj) {
		obj.setEstatus('E');
		funcionDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioFuncion.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("estatus", estatus[0]));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return funcionDAO.findByCriterions(Funcion.class,
				restricciones, orden);
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
		return funcionDAO.findByCriterions(Funcion.class,
				restricciones, orden);
	}

	/**
	 * Busca por id
	 */
	@Override
	public Funcion buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("id", valor));
		List busqueda = funcionDAO.findByCriterions(Funcion.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Funcion) busqueda.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List buscarPadres() {
		List restricciones = new ArrayList();
		restricciones.add(Restrictions.isNull("funcion"));
		List orden = new ArrayList();
		orden.add(Order.asc("orden"));

		return funcionDAO.findByCriterions(Funcion.class,
				restricciones, orden);
	}
	
	@Override
	public List buscarFunciones() {
		List restricciones = new ArrayList();
		restricciones.add(Restrictions.ilike("id", "F_", MatchMode.ANYWHERE));
		restricciones.add(Restrictions.not(Restrictions.eq("id", "F_CCT")));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));

		return funcionDAO.findByCriterions(Funcion.class,
				restricciones, orden);
	}

}
