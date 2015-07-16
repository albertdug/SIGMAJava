package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.HospitalDAO;
import org.ucla.sigma.dao.NeurologiaDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioHospital;
import org.ucla.sigma.interfazservicio.IServicioNeurologia;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.Hospital;
import org.ucla.sigma.modelo.Hospital;
import org.ucla.sigma.modelo.Boton;

public class ServicioHospital implements IServicioHospital, Serializable {

	private HospitalDAO hospitalDAO;

	public HospitalDAO getHospitalDAO() {
		return hospitalDAO;
	}

	public void setHospitalDAO(HospitalDAO hospitalDAO) {
		this.hospitalDAO = hospitalDAO;
	}

	@Override
	public Hospital buscarUnico() {
		List restricciones = new ArrayList();
		restricciones.add(Restrictions.eq("estatus", 'A'));
		List busqueda = hospitalDAO.findByCriterions(Hospital.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Hospital) busqueda.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void guardarHospital(Hospital hospital) {
		hospitalDAO.saveOrUpdate(hospital);

	}

}
