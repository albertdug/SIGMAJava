package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.CardiacaDAO;
import org.ucla.sigma.interfazservicio.IServicioCardiaca;
import org.ucla.sigma.modelo.Cardiaca;

public class ServicioCardiaca implements IServicioCardiaca, Serializable {

	private CardiacaDAO cardiacaDAO;

	public CardiacaDAO getCardiacaDAO() {
		return cardiacaDAO;
	}

	public void setCardiacaDAO(CardiacaDAO cardiacaDAO) {
		this.cardiacaDAO = cardiacaDAO;
	}
	
	@Override
	public void guardarCardiaca(Cardiaca obj) {
		obj.setEstatus('A');
		cardiacaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarCardiaca(Cardiaca obj) {
		obj.setEstatus('E');
		cardiacaDAO.saveOrUpdate(obj);
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
		return cardiacaDAO.findByCriterions(Cardiaca.class, restricciones, orden);
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
		return cardiacaDAO.findByCriterions(Cardiaca.class, restricciones, orden);
	}

	@Override
	public Cardiaca buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = cardiacaDAO.findByCriterions(Cardiaca.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Cardiaca) busqueda.get(0);
		} else {
			return null;
		}
	}


}
