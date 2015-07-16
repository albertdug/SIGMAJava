package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Hospital;

public interface IServicioHospital {
	
	public Hospital buscarUnico();

	public void guardarHospital(Hospital hospital);
	
}
