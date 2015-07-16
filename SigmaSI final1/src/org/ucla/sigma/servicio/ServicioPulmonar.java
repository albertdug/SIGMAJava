package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.PulmonarDAO;
import org.ucla.sigma.interfazservicio.IServicioPulmonar;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Pulmonar;

public class ServicioPulmonar implements IServicioPulmonar, Serializable {

	private PulmonarDAO pulmonarDAO;

	public PulmonarDAO getPulmonarDAO() {
		return pulmonarDAO;
	}

	public void setPulmonarDAO(PulmonarDAO pulmonarDAO) {
		this.pulmonarDAO = pulmonarDAO;
	}
	
	@Override
	public void guardarPulmonar(Pulmonar obj) {
		obj.setEstatus('A');
		pulmonarDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarPulmonar(Pulmonar obj) {
		obj.setEstatus('E');
		pulmonarDAO.saveOrUpdate(obj);
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
		return pulmonarDAO.findByCriterions(Pulmonar.class, restricciones, orden);
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
		return pulmonarDAO.findByCriterions(Pulmonar.class, restricciones, orden);
	}

	@Override
	public Pulmonar buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = pulmonarDAO.findByCriterions(Pulmonar.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Pulmonar) busqueda.get(0);
		} else {
			return null;
		}
	}


}
