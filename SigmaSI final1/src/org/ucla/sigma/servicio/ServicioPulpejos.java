package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.PulpejosDAO;
import org.ucla.sigma.interfazservicio.IServicioPulpejos;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.Pulpejos;

public class ServicioPulpejos implements IServicioPulpejos, Serializable {

	private PulpejosDAO pulpejosDAO;

	public PulpejosDAO getPulpejosDAO() {
		return pulpejosDAO;
	}

	public void setPulpejosDAO(PulpejosDAO pulpejosDAO) {
		this.pulpejosDAO = pulpejosDAO;
	}

	@Override
	public void guardarPulpejos(Pulpejos obj) {
		obj.setEstatus('A');
		pulpejosDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarPulpejos(Pulpejos obj) {
		obj.setEstatus('E');
		pulpejosDAO.saveOrUpdate(obj);
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
		return pulpejosDAO.findByCriterions(Pulpejos.class, restricciones, orden);
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
		return pulpejosDAO.findByCriterions(Pulpejos.class, restricciones, orden);
	}

	@Override
	public Pulpejos buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = pulpejosDAO.findByCriterions(Pulpejos.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Pulpejos) busqueda.get(0);
		} else {
			return null;
		}
	}

}
