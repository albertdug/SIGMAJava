package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.MembranaMucosaDAO;
import org.ucla.sigma.interfazservicio.IServicioMembranaMucosa;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.MembranaMucosa;

public class ServicioMembranaMucosa implements IServicioMembranaMucosa, Serializable {

	private MembranaMucosaDAO membranamucosaDAO;

	public MembranaMucosaDAO getMembranaMucosaDAO() {
		return membranamucosaDAO;
	}

	public void setMembranaMucosaDAO(MembranaMucosaDAO membranamucosaDAO) {
		this.membranamucosaDAO = membranamucosaDAO;
	}

	@Override
	public void guardarMembranaMucosa(MembranaMucosa obj) {
		obj.setEstatus('A');
		membranamucosaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarMembranaMucosa(MembranaMucosa obj) {
		obj.setEstatus('E');
		membranamucosaDAO.saveOrUpdate(obj);
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
		return membranamucosaDAO.findByCriterions(MembranaMucosa.class, restricciones, orden);
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
		return membranamucosaDAO.findByCriterions(MembranaMucosa.class, restricciones, orden);
	}

	@Override
	public MembranaMucosa buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = membranamucosaDAO.findByCriterions(MembranaMucosa.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (MembranaMucosa) busqueda.get(0);
		} else {
			return null;
		}
	}

}
