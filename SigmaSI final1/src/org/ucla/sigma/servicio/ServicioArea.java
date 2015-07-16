package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.AreaDAO;
import org.ucla.sigma.interfazservicio.IServicioArea;
import org.ucla.sigma.modelo.Area;

public class ServicioArea implements IServicioArea, Serializable {

	private AreaDAO areaDAO;

	public AreaDAO getAreaDAO() {
		return areaDAO;
	}

	public void setAreaDAO(AreaDAO areaDAO) {
		this.areaDAO = areaDAO;
	}
	
	@Override
	public void guardarArea(Area obj) {
		obj.setEstatus('A');
		areaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarArea(Area obj) {
		obj.setEstatus('E');
		areaDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioArea.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return areaDAO.findByCriterions(Area.class, restricciones, orden);
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
		return areaDAO.findByCriterions(Area.class, restricciones, orden);
	}

	@Override
	public Area buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = areaDAO.findByCriterions(Area.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Area) busqueda.get(0);
		} else {
			return null;
		}
	}
	
}
