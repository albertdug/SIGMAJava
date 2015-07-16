package org.ucla.sigma.interfazservicio;

import org.ucla.sigma.modelo.ConsultaGeneral;
import org.ucla.sigma.modelo.Paciente;

public interface IServicioConsultaGeneral {
	
	public void guardarConsultaG(ConsultaGeneral obj);
	
	public ConsultaGeneral buscarUno(int valor, char... estatus);
	
	public ConsultaGeneral buscarUltimaConsultaGPaciente(Paciente numHistoria, char... estatus);
	
	public ConsultaGeneral buscarCastracionConsultaGPaciente(Paciente numHistoria, char... estatus);
	
}
