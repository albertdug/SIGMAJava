package org.ucla.sigma.interfazservicio;

import org.ucla.sigma.modelo.Carnet;

public interface IServicioCarnet {


	Carnet buscarUltimo(String historia, char[] estatus);

	void guardarCarnet(Carnet obj);
	
}
