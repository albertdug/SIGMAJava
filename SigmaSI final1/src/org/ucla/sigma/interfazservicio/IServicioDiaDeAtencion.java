package org.ucla.sigma.interfazservicio;

import org.ucla.sigma.modelo.DiaDeAtencion;

public interface IServicioDiaDeAtencion {
	
	public DiaDeAtencion buscarUno(String valor, char estatus);
	
}
