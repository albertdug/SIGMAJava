package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.LesionPSDAO;
import org.ucla.sigma.interfazservicio.IServicioLesionPS;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.LesionPS;

public class ServicioLesionPS implements IServicioLesionPS, Serializable {

	private LesionPSDAO lesionpsDAO;

	public LesionPSDAO getLesionPSDAO() {
		return lesionpsDAO;
	}

	public void setLesionPSDAO(LesionPSDAO lesionpsDAO) {
		this.lesionpsDAO = lesionpsDAO;
	}

	@Override
	public void guardarLesionPS(LesionPS obj) {
		obj.setEstatus('A');
		lesionpsDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarLesionPS(LesionPS obj) {
		obj.setEstatus('E');
		lesionpsDAO.saveOrUpdate(obj);
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
		return lesionpsDAO.findByCriterions(LesionPS.class, restricciones, orden);
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
		return lesionpsDAO.findByCriterions(LesionPS.class, restricciones, orden);
	}

	@Override
	public LesionPS buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = lesionpsDAO.findByCriterions(LesionPS.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (LesionPS) busqueda.get(0);
		} else {
			return null;
		}
	}

}
