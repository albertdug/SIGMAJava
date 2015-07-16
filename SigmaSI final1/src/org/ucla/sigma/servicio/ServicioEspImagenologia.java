package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.AdjuntoDAO;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.CardiologiaDAO;
import org.ucla.sigma.dao.DiaDeAtencionDAO;
import org.ucla.sigma.dao.EspImagenologiaDAO;
import org.ucla.sigma.dao.HospitalDifusionDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioAdjunto;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioCardiologia;
import org.ucla.sigma.interfazservicio.IServicioDiaDeAtencion;
import org.ucla.sigma.interfazservicio.IServicioEspImagenologia;
import org.ucla.sigma.interfazservicio.IServicioHospitalDifusion;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.EspImagenologia;
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.modelo.Raza;
import org.ucla.sigma.modelo.TipoImagenologia;
import org.ucla.sigma.modelo.TipoTratamiento;
import org.ucla.sigma.modelo.Tratamiento;

public class ServicioEspImagenologia implements IServicioEspImagenologia, Serializable {

	private EspImagenologiaDAO espImagenologiaDAO;

	public EspImagenologiaDAO getEspImagenologiaDAO() {
		return espImagenologiaDAO;
	}

	public void setEspImagenologiaDAO(EspImagenologiaDAO espImagenologiaDAO) {
		this.espImagenologiaDAO = espImagenologiaDAO;
	}

	@Override
	public void guardarEspImagenologia(EspImagenologia obj) {
		obj.setEstatus('A');
		espImagenologiaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarEspImagenologia(EspImagenologia obj) {
		obj.setEstatus('E');
		espImagenologiaDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioEspImagenologia.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return espImagenologiaDAO.findByCriterions(EspImagenologia.class, restricciones, orden);
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
		return espImagenologiaDAO.findByCriterions(EspImagenologia.class, restricciones, orden);
	}

	@Override
	public EspImagenologia buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = espImagenologiaDAO.findByCriterions(EspImagenologia.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (EspImagenologia) busqueda.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public List buscarCoincidenciasTipo(TipoImagenologia valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("tipoImagenologia", valor));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return espImagenologiaDAO.findByCriterions(EspImagenologia.class, restricciones, orden);
	}
	
	@Override
	public List buscarEstudiosAsociados(TipoImagenologia valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("tipoImagenologia", valor));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return espImagenologiaDAO.findByCriterions(EspImagenologia.class, restricciones, orden);
	}
}
