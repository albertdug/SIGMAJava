package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.OtrasCaracteristicasDAO;
import org.ucla.sigma.interfazservicio.IServicioOtrasCaracteristicas;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.OtrasCaracteristicas;

public class ServicioOtrasCaracteristicas implements IServicioOtrasCaracteristicas, Serializable {

	private OtrasCaracteristicasDAO otrascaracteristicasDAO;

	public OtrasCaracteristicasDAO getOtrasCaracteristicasDAO() {
		return otrascaracteristicasDAO;
	}

	public void setOtrasCaracteristicasDAO(OtrasCaracteristicasDAO otrascaracteristicasDAO) {
		this.otrascaracteristicasDAO = otrascaracteristicasDAO;
	}

	@Override
	public void guardarOtrasCaracteristicas(OtrasCaracteristicas obj) {
		obj.setEstatus('A');
		otrascaracteristicasDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarOtrasCaracteristicas(OtrasCaracteristicas obj) {
		obj.setEstatus('E');
		otrascaracteristicasDAO.saveOrUpdate(obj);
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
		return otrascaracteristicasDAO.findByCriterions(OtrasCaracteristicas.class, restricciones, orden);
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
		return otrascaracteristicasDAO.findByCriterions(OtrasCaracteristicas.class, restricciones, orden);
	}

	@Override
	public OtrasCaracteristicas buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = otrascaracteristicasDAO.findByCriterions(OtrasCaracteristicas.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (OtrasCaracteristicas) busqueda.get(0);
		} else {
			return null;
		}
	}

}
