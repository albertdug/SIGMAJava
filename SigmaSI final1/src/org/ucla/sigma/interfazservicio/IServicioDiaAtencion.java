package org.ucla.sigma.interfazservicio;

import org.ucla.sigma.modelo.DiaDeAtencion;

public interface IServicioDiaAtencion {

	public DiaDeAtencion buscarUno(String valor, char[] estatus);
	
}
