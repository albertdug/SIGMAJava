package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.ImagenologiaDAO;
import org.ucla.sigma.dao.NeurologiaDAO;
import org.ucla.sigma.dao.NotificacionDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioImagenologia;
import org.ucla.sigma.interfazservicio.IServicioNeurologia;
import org.ucla.sigma.interfazservicio.IServicioNotificacion;
import org.ucla.sigma.interfazservicio.IServicioPaciente;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.Imagenologia;
import org.ucla.sigma.modelo.Paciente;

public class ServicioImagenologia implements IServicioImagenologia, Serializable {

	private ImagenologiaDAO imagenologiaDAO;

	public ImagenologiaDAO getImagenologiaDAO() {
		return imagenologiaDAO;
	}

	public void setImagenologiaDAO(ImagenologiaDAO imagenologiaDAO) {
		this.imagenologiaDAO = imagenologiaDAO;
	}

	@Override
	public void guardarImagenologia(Imagenologia obj) {
		obj.setEstatus('A');
		imagenologiaDAO.saveOrUpdate(obj);
		
	}
	

}
