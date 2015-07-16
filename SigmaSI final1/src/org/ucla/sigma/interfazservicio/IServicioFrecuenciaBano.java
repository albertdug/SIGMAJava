package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.FrecuenciaBano;

public interface IServicioFrecuenciaBano {
	
	public void guardarFrecuenciaBano(FrecuenciaBano obj);

	public void borrarFrecuenciaBano(FrecuenciaBano obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public FrecuenciaBano buscarUno(String valor, char... estatus);
	
}
