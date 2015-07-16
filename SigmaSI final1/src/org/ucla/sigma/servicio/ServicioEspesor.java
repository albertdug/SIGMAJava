package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.EspesorDAO;
import org.ucla.sigma.interfazservicio.IServicioEspesor;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.Espesor;

public class ServicioEspesor implements IServicioEspesor, Serializable {

	private EspesorDAO espesorDAO;

	public EspesorDAO getEspesorDAO() {
		return espesorDAO;
	}

	public void setEspesorDAO(EspesorDAO espesorDAO) {
		this.espesorDAO = espesorDAO;
	}

	@Override
	public void guardarEspesor(Espesor obj) {
		obj.setEstatus('A');
		espesorDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarEspesor(Espesor obj) {
		obj.setEstatus('E');
		espesorDAO.saveOrUpdate(obj);
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
		return espesorDAO.findByCriterions(Espesor.class, restricciones, orden);
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
		return espesorDAO.findByCriterions(Espesor.class, restricciones, orden);
	}

	@Override
	public Espesor buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = espesorDAO.findByCriterions(Espesor.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Espesor) busqueda.get(0);
		} else {
			return null;
		}
	}

}


