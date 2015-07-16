package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.PalpacionAbdominalDAO;
import org.ucla.sigma.interfazservicio.IServicioPalpacionAbdominal;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.PalpacionAbdominal;

public class ServicioPalpacionAbdominal implements IServicioPalpacionAbdominal, Serializable {

	private PalpacionAbdominalDAO palpacionabdominalDAO;

	public PalpacionAbdominalDAO getPalpacionAbdominalDAO() {
		return palpacionabdominalDAO;
	}

	public void setPalpacionAbdominalDAO(PalpacionAbdominalDAO palpacionabdominalDAO) {
		this.palpacionabdominalDAO = palpacionabdominalDAO;
	}

	@Override
	public void guardarPalpacionAbdominal(PalpacionAbdominal obj) {
		obj.setEstatus('A');
		palpacionabdominalDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarPalpacionAbdominal(PalpacionAbdominal obj) {
		obj.setEstatus('E');
		palpacionabdominalDAO.saveOrUpdate(obj);
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
		return palpacionabdominalDAO.findByCriterions(PalpacionAbdominal.class, restricciones, orden);
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
		return palpacionabdominalDAO.findByCriterions(PalpacionAbdominal.class, restricciones, orden);
	}

	@Override
	public PalpacionAbdominal buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = palpacionabdominalDAO.findByCriterions(PalpacionAbdominal.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (PalpacionAbdominal) busqueda.get(0);
		} else {
			return null;
		}
	}

}
