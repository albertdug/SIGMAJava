package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.FichaMedica;
import org.ucla.sigma.modelo.Paciente;

public interface IServicioFichaMedica {
	
	public void guardarFichaMedica(FichaMedica obj);
	
	public FichaMedica buscarUno(int valor, char... estatus);

	public List buscarFichaMedicaPaciente(Paciente numHistoria, char... estatus);
	
}
