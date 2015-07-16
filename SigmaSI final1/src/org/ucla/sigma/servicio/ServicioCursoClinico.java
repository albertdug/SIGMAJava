package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.CursoClinicoDAO;
import org.ucla.sigma.interfazservicio.IServicioCursoClinico;
import org.ucla.sigma.modelo.CursoClinico;

public class ServicioCursoClinico implements IServicioCursoClinico, Serializable {

	private CursoClinicoDAO cursoClinicoDAO;

	public CursoClinicoDAO getCursoClinicoDAO() {
		return cursoClinicoDAO;
	}

	public void setCursoClinicoDAO(CursoClinicoDAO cursoClinicoDAO) {
		this.cursoClinicoDAO = cursoClinicoDAO;
	}
	
	@Override
	public void guardarCursoClinico(CursoClinico obj) {
		obj.setEstatus('A');
		cursoClinicoDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarCursoClinico(CursoClinico obj) {
		obj.setEstatus('E');
		cursoClinicoDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioCursoClinico.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return cursoClinicoDAO.findByCriterions(CursoClinico.class, restricciones, orden);
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
		return cursoClinicoDAO.findByCriterions(CursoClinico.class, restricciones, orden);
	}

	@Override
	public CursoClinico buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = cursoClinicoDAO.findByCriterions(CursoClinico.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (CursoClinico) busqueda.get(0);
		} else {
			return null;
		}
	}
}
