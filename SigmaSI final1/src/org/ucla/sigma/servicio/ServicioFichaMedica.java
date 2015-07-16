package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.FichaMedicaDAO;
import org.ucla.sigma.interfazservicio.IServicioFichaMedica;
import org.ucla.sigma.modelo.ConsultaGeneral;
import org.ucla.sigma.modelo.FichaMedica;
import org.ucla.sigma.modelo.Historial;
import org.ucla.sigma.modelo.Paciente;

public class ServicioFichaMedica implements IServicioFichaMedica, Serializable {

	private FichaMedicaDAO fichaMedicaDAO;

	public FichaMedicaDAO getFichaMedicaDAO() {
		return fichaMedicaDAO;
	}

	public void setFichaMedicaDAO(FichaMedicaDAO fichaMedicaDAO) {
		this.fichaMedicaDAO = fichaMedicaDAO;
	}
	
	@Override
	public void guardarFichaMedica(FichaMedica obj) {
		fichaMedicaDAO.saveOrUpdate(obj);
		
	}

	@Override
	public FichaMedica buscarUno(int valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("id", valor));
		List busqueda = fichaMedicaDAO.findByCriterions(FichaMedica.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (FichaMedica) busqueda.get(0);
		} else {
			return null;
		}
	}

	public List buscarFichaMedicaPaciente(Paciente numHistoria, char... estatus){
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("paciente", numHistoria));
		return fichaMedicaDAO.findByCriterions(FichaMedica.class, restricciones);
	}
	
}
