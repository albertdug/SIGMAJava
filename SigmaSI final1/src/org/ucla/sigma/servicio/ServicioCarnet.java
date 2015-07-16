package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.CarnetDAO;
import org.ucla.sigma.dao.DifusionDAO;
import org.ucla.sigma.dao.ImagenologiaDAO;
import org.ucla.sigma.dao.NeurologiaDAO;
import org.ucla.sigma.dao.NotificacionDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioCarnet;
import org.ucla.sigma.interfazservicio.IServicioDifusion;
import org.ucla.sigma.interfazservicio.IServicioImagenologia;
import org.ucla.sigma.interfazservicio.IServicioNeurologia;
import org.ucla.sigma.interfazservicio.IServicioNotificacion;
import org.ucla.sigma.interfazservicio.IServicioPaciente;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.Adjunto;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.Carnet;
import org.ucla.sigma.modelo.Paciente;


public class ServicioCarnet implements IServicioCarnet, Serializable {

	private CarnetDAO carnetDAO;

	public CarnetDAO getCarnetDAO() {
		return carnetDAO;
	}

	public void setCarnetDAO(CarnetDAO carnetDAO) {
		this.carnetDAO = carnetDAO;
	}
	
	@Override
	public void guardarCarnet(Carnet obj) {
		obj.setEstatus('A');
		carnetDAO.saveOrUpdate(obj);
	}
	
	@Override
	public Carnet buscarUltimo(String historia, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("paciente.historiaMedica",  historia));
		List orden = new ArrayList();
		orden.add(Order.desc("expedicion"));
		return (Carnet) carnetDAO.findByCriterions(Carnet.class, restricciones, orden).get(0);
	}

}
