package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.ExamenDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioExamen;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.Examen;

public class ServicioExamen implements IServicioExamen, Serializable {

	private ExamenDAO examenDAO;

	public ExamenDAO getExamenDAO() {
		return examenDAO;
	}

	public void setExamenDAO(ExamenDAO examenDAO) {
		this.examenDAO = examenDAO;
	}
	
	@Override
	public void guardarExamen(Examen obj) {
		obj.setEstatus('A');
		examenDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarExamen(Examen obj) {
		obj.setEstatus('E');
		examenDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioExamen.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return examenDAO.findByCriterions(Examen.class, restricciones, orden);
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
		return examenDAO.findByCriterions(Examen.class, restricciones, orden);
	}

	@Override
	public Examen buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = examenDAO.findByCriterions(Examen.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Examen) busqueda.get(0);
		} else {
			return null;
		}
	}

	
}
