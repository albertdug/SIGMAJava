package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.CardiologiaDAO;
import org.ucla.sigma.dao.DiaDeAtencionDAO;
import org.ucla.sigma.dao.HospitalDifusionDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioCardiologia;
import org.ucla.sigma.interfazservicio.IServicioDiaDeAtencion;
import org.ucla.sigma.interfazservicio.IServicioHospitalDifusion;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.Cardiologia;

public class ServicioCardiologia implements IServicioCardiologia, Serializable {

	private CardiologiaDAO cardiologiaDAO;

	public CardiologiaDAO getCardiologiaDAO() {
		return cardiologiaDAO;
	}

	public void setCardiologiaDAO(CardiologiaDAO cardiologiaDAO) {
		this.cardiologiaDAO = cardiologiaDAO;
	}

	@Override
	public void guardarCardiologia(Cardiologia obj) {
		obj.setEstatus('A');
		cardiologiaDAO.saveOrUpdate(obj);
	}
	
}
