package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.ConsultaGeneralDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioConsultaGeneral;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.ConsultaGeneral;
import org.ucla.sigma.modelo.Historial;
import org.ucla.sigma.modelo.Paciente;

public class ServicioConsultaGeneral implements IServicioConsultaGeneral, Serializable {

	private ConsultaGeneralDAO consultaGeneralDAO;

	public ConsultaGeneralDAO getConsultaGeneralDAO() {
		return consultaGeneralDAO;
	}

	public void setConsultaGeneralDAO(ConsultaGeneralDAO consultaGeneralDAO) {
		this.consultaGeneralDAO = consultaGeneralDAO;
	}

	@Override
	public void guardarConsultaG(ConsultaGeneral obj) {
		consultaGeneralDAO.saveOrUpdate(obj);
		
	}

	@Override
	public ConsultaGeneral buscarUno(int valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("id", valor));
		List busqueda = consultaGeneralDAO.findByCriterions(ConsultaGeneral.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (ConsultaGeneral) busqueda.get(0);
		} else {
			return null;
		}
	}
	
	public ConsultaGeneral buscarUltimaConsultaGPaciente(Paciente numHistoria, char... estatus){
		List restricciones = new ArrayList();
		List orden = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("paciente", numHistoria));
		orden.add(Order.desc("fecha"));
		orden.add(Order.desc("hora"));
		List busqueda = consultaGeneralDAO.findByCriterions(ConsultaGeneral.class, restricciones, orden);
		if (!busqueda.isEmpty()) {
			return (ConsultaGeneral) busqueda.get(0);
		} else {
			return null;
		}
	}
	
	public ConsultaGeneral buscarCastracionConsultaGPaciente(Paciente numHistoria, char... estatus){
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("paciente", numHistoria));
		restricciones.add(Restrictions.isNotNull("castracion"));
		List busqueda = consultaGeneralDAO.findByCriterions(ConsultaGeneral.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (ConsultaGeneral) busqueda.get(0);
		} else {
			return null;
		}
	}
	
}