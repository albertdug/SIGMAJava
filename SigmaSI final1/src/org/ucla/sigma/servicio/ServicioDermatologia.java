package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.DermatologiaDAO;
import org.ucla.sigma.dao.HospitalDAO;
import org.ucla.sigma.dao.NeurologiaDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioDermatologia;
import org.ucla.sigma.interfazservicio.IServicioHospital;
import org.ucla.sigma.interfazservicio.IServicioNeurologia;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.ConsultaGeneral;
import org.ucla.sigma.modelo.Dermatologia;

public class ServicioDermatologia implements IServicioDermatologia, Serializable {

	private DermatologiaDAO dermatologiaDAO;

	public DermatologiaDAO getDermatologiaDAO() {
		return dermatologiaDAO;
	}

	public void setDermatologiaDAO(DermatologiaDAO dermatologiaDAO) {
		this.dermatologiaDAO = dermatologiaDAO;
	}

	@Override
	public void guardarDermatologia(Dermatologia obj) {
		obj.setEstatus('A');
		dermatologiaDAO.saveOrUpdate(obj);
	}
	
	@Override
	public Dermatologia buscarUno(int valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("id", valor));
		List busqueda = dermatologiaDAO.findByCriterions(Dermatologia.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Dermatologia) busqueda.get(0);
		} else {
			return null;
		}
	}

	
}
