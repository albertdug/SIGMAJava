package org.ucla.sigma.interfazservicio;

import org.ucla.sigma.modelo.Dermatologia;

public interface IServicioDermatologia {
	
	public void guardarDermatologia(Dermatologia obj);
	
	public Dermatologia buscarUno(int valor, char... estatus);	
	
}
