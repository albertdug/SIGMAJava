package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.HospitalDifusion;


public interface IServicioHospitalDifusion {
	
	public void guardarHospitalDifusion(HospitalDifusion obj);

	public void borrarHospitalDifusion(HospitalDifusion obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public HospitalDifusion buscarUno(String valor, char... estatus);
	
}
