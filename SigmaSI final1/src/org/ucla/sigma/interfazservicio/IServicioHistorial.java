package org.ucla.sigma.interfazservicio;

import org.ucla.sigma.modelo.Historial;

public interface IServicioHistorial {
	public void guardarHistorial(Historial obj);
	public Historial buscarUno(int valor, char... estatus);
}
