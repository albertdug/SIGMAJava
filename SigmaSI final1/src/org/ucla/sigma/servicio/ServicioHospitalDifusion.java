package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.HospitalDifusionDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioHospitalDifusion;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.HospitalDifusion;

public class ServicioHospitalDifusion implements IServicioHospitalDifusion, Serializable {

	private HospitalDifusionDAO hospitalDifusionDAO;

	public HospitalDifusionDAO getHospitalDifusionDAO() {
		return hospitalDifusionDAO;
	}

	public void setHospitalDifusionDAO(HospitalDifusionDAO hospitalDifusionDAO) {
		this.hospitalDifusionDAO = hospitalDifusionDAO;
	}
	

	@Override
	public void guardarHospitalDifusion(HospitalDifusion obj) {
		obj.setEstatus('A');
		hospitalDifusionDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarHospitalDifusion(HospitalDifusion obj) {
		obj.setEstatus('E');
		hospitalDifusionDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioHospitalDifusion.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("enlace"));
		return hospitalDifusionDAO.findByCriterions(HospitalDifusion.class, restricciones, orden);
	}

	@Override
	public List buscarCoincidencias(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("enlace", valor,
				MatchMode.ANYWHERE));
		List orden = new ArrayList();
		orden.add(Order.asc("enlace"));
		return hospitalDifusionDAO.findByCriterions(HospitalDifusion.class, restricciones, orden);
	}

	@Override
	public HospitalDifusion buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("enlace", valor));
		List busqueda = hospitalDifusionDAO.findByCriterions(HospitalDifusion.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (HospitalDifusion) busqueda.get(0);
		} else {
			return null;
		}
	}

	
}
