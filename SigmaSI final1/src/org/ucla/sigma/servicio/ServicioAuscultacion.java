package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.AuscultacionDAO;
import org.ucla.sigma.interfazservicio.IServicioAuscultacion;
import org.ucla.sigma.modelo.Auscultacion;

public class ServicioAuscultacion implements IServicioAuscultacion, Serializable {

	private AuscultacionDAO auscultacionDAO;

	public AuscultacionDAO getAuscultacionDAO() {
		return auscultacionDAO;
	}

	public void setAuscultacionDAO(AuscultacionDAO auscultacionDAO) {
		this.auscultacionDAO = auscultacionDAO;
	}
	
	@Override
	public void guardarAuscultacion(Auscultacion obj) {
		obj.setEstatus('A');
		auscultacionDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarAuscultacion(Auscultacion obj) {
		obj.setEstatus('E');
		auscultacionDAO.saveOrUpdate(obj);
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
		return auscultacionDAO.findByCriterions(Auscultacion.class, restricciones, orden);
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
		return auscultacionDAO.findByCriterions(Auscultacion.class, restricciones, orden);
	}

	@Override
	public Auscultacion buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = auscultacionDAO.findByCriterions(Auscultacion.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Auscultacion) busqueda.get(0);
		} else {
			return null;
		}
	}


}
