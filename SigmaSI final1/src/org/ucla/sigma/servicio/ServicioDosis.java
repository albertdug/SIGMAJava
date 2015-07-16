package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.DosisDAO;
import org.ucla.sigma.interfazservicio.IServicioDosis;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.Dosis;

public class ServicioDosis implements IServicioDosis, Serializable {

	private DosisDAO dosisDAO;

	public DosisDAO getDosisDAO() {
		return dosisDAO;
	}

	public void setDosisDAO(DosisDAO dosisDAO) {
		this.dosisDAO = dosisDAO;
	}

	@Override
	public void guardarDosis(Dosis obj) {
		obj.setEstatus('A');
		dosisDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarDosis(Dosis obj) {
		obj.setEstatus('E');
		dosisDAO.saveOrUpdate(obj);
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
		return dosisDAO.findByCriterions(Dosis.class, restricciones, orden);
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
		return dosisDAO.findByCriterions(Dosis.class, restricciones, orden);
	}

	@Override
	public Dosis buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = dosisDAO.findByCriterions(Dosis.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Dosis) busqueda.get(0);
		} else {
			return null;
		}
	}

}


