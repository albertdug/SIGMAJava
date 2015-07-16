package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.CirugiaDAO;
import org.ucla.sigma.dao.CirugiaTransquirurgicoDAO;
import org.ucla.sigma.dao.EntidadExternaDAO;
import org.ucla.sigma.dao.ImagenDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioCirugia;
import org.ucla.sigma.interfazservicio.IServicioCirugiaTransquirurgico;
import org.ucla.sigma.interfazservicio.IServicioEntidadExterna;
import org.ucla.sigma.interfazservicio.IServicioImagen;
import org.ucla.sigma.interfazservicio.IServicioPaciente;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.CirugiaTransquirurgico;
import org.ucla.sigma.modelo.Paciente;

public class ServicioCirugiaTransquirurgico implements IServicioCirugiaTransquirurgico, Serializable {

	private CirugiaTransquirurgicoDAO cirugiaTransquirurgicoDAO;

	public CirugiaTransquirurgicoDAO getCirugiaTransquirurgicoDAO() {
		return cirugiaTransquirurgicoDAO;
	}

	public void setCirugiaTransquirurgicoDAO(
			CirugiaTransquirurgicoDAO cirugiaTransquirurgicoDAO) {
		this.cirugiaTransquirurgicoDAO = cirugiaTransquirurgicoDAO;
	}

	@Override
	public void guardarCirugiaTransquirurgico(CirugiaTransquirurgico obj) {
		cirugiaTransquirurgicoDAO.saveOrUpdate(obj);
		
	}
	
}
