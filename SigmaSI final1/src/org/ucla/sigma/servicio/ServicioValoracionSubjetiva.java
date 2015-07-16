package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.ValoracionSubjetivaDAO;
import org.ucla.sigma.interfazservicio.IServicioValoracionSubjetiva;
import org.ucla.sigma.modelo.ValoracionSubjetiva;

public class ServicioValoracionSubjetiva implements IServicioValoracionSubjetiva, Serializable {

	private ValoracionSubjetivaDAO valoracionSubjetivaDAO;

	public ValoracionSubjetivaDAO getValoracionSubjetivaDAO() {
		return valoracionSubjetivaDAO;
	}

	public void setValoracionSubjetivaDAO(ValoracionSubjetivaDAO valoracionSubjetivaDAO) {
		this.valoracionSubjetivaDAO = valoracionSubjetivaDAO;
	}

	@Override
	public void guardarValoracionSubjetiva(ValoracionSubjetiva obj) {
		obj.setEstatus('A');
		valoracionSubjetivaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarValoracionSubjetiva(ValoracionSubjetiva obj) {
		obj.setEstatus('E');
		valoracionSubjetivaDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioValoracionSubjetiva.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return valoracionSubjetivaDAO.findByCriterions(ValoracionSubjetiva.class, restricciones, orden);
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
		return valoracionSubjetivaDAO.findByCriterions(ValoracionSubjetiva.class, restricciones, orden);
	}

	@Override
	public ValoracionSubjetiva buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = valoracionSubjetivaDAO.findByCriterions(ValoracionSubjetiva.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (ValoracionSubjetiva) busqueda.get(0);
		} else {
			return null;
		}
	}

}
