package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.ConsultaGeneralDAO;
import org.ucla.sigma.dao.HistorialDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioConsultaGeneral;
import org.ucla.sigma.interfazservicio.IServicioHistorial;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.ConsultaGeneral;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.FichaMedica;
import org.ucla.sigma.modelo.Historial;

public class ServicioHistorial implements IServicioHistorial, Serializable {

	private HistorialDAO historialDAO;

	public HistorialDAO getHistorialDAO() {
		return historialDAO;
	}

	public void setHistorialDAO(HistorialDAO historialDAO) {
		this.historialDAO = historialDAO;
	}

	@Override
	public void guardarHistorial(Historial obj) {
		historialDAO.saveOrUpdate(obj);

	}

	@Override
	public Historial buscarUno(int valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("id", valor));
		List busqueda = historialDAO.findByCriterions(Historial.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Historial) busqueda.get(0);
		} else {
			return null;
		}
	}

	public List buscarServicioPaciente(String numHistoria, String tipServicio,
			char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("paciente.historiaMedica",
				numHistoria));
		restricciones.add(Restrictions.eq("tipoServicio.id", tipServicio));
		return historialDAO.findByCriterions(Historial.class, restricciones);
	}
	
	public List buscarServicioPacienteHistorial(String numHistoria,	char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("paciente.historiaMedica",
				numHistoria));
		return historialDAO.findByCriterions(Historial.class, restricciones);
	}

	public Historial buscarUltimoServicioPaciente(String numHistoria,
			String tipServicio, char... estatus) {
		List restricciones = new ArrayList();
		List orden = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("paciente.historiaMedica",
				numHistoria));
		restricciones.add(Restrictions.eq("tipoServicio.id", tipServicio));
		orden.add(Order.desc("fecha"));
		orden.add(Order.desc("hora"));
		List busqueda = historialDAO.findByCriterions(Historial.class,
				restricciones, orden);
		if (!busqueda.isEmpty()) {
			return (Historial) busqueda.get(0);
		} else {
			return null;
		}
	}

	public List<Historial> buscarHistorialHQL(String condicion) {

		String hql = "select distinct h from Historial h join h.patologias pt join pt.tipoPatologia tpt join h.paciente p join p.sexo sx join p.raza r  join r.especie es join h.tipoServicio ts join ts.servicios ser join h.patologias pat join pat.tipoPatologia tpat"
				+ condicion;

		System.out.println(hql);
		return historialDAO.findByHQLQuery(hql);
	}
}
