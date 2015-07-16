package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.RitmoDAO;
import org.ucla.sigma.interfazservicio.IServicioRitmo;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Ritmo;

public class ServicioRitmo implements IServicioRitmo, Serializable {

	private RitmoDAO ritmoDAO;

	public RitmoDAO getRitmoDAO() {
		return ritmoDAO;
	}

	public void setRitmoDAO(RitmoDAO ritmoDAO) {
		this.ritmoDAO = ritmoDAO;
	}
	
	@Override
	public void guardarRitmo(Ritmo obj) {
		obj.setEstatus('A');
		ritmoDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarRitmo(Ritmo obj) {
		obj.setEstatus('E');
		ritmoDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioEspecie.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return ritmoDAO.findByCriterions(Ritmo.class, restricciones, orden);
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
		return ritmoDAO.findByCriterions(Ritmo.class, restricciones, orden);
	}

	@Override
	public Ritmo buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = ritmoDAO.findByCriterions(Ritmo.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Ritmo) busqueda.get(0);
		} else {
			return null;
		}
	}


}
