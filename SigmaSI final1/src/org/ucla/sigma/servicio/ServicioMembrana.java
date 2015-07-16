package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.MembranaDAO;
import org.ucla.sigma.interfazservicio.IServicioMembrana;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Membrana;

public class ServicioMembrana implements IServicioMembrana, Serializable {

	private MembranaDAO membranaDAO;

	public MembranaDAO getMembranaDAO() {
		return membranaDAO;
	}

	public void setMembranaDAO(MembranaDAO membranaDAO) {
		this.membranaDAO = membranaDAO;
	}
	
	@Override
	public void guardarMembrana(Membrana obj) {
		obj.setEstatus('A');
		membranaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarMembrana(Membrana obj) {
		obj.setEstatus('E');
		membranaDAO.saveOrUpdate(obj);
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
		return membranaDAO.findByCriterions(Membrana.class, restricciones, orden);
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
		return membranaDAO.findByCriterions(Membrana.class, restricciones, orden);
	}

	@Override
	public Membrana buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = membranaDAO.findByCriterions(Membrana.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Membrana) busqueda.get(0);
		} else {
			return null;
		}
	}


}
