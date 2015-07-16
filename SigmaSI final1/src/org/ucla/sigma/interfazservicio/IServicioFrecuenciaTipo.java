package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.FrecuenciaTipo;

public interface IServicioFrecuenciaTipo {
	
	public void guardarFrecuenciaTipo(FrecuenciaTipo obj);

	public void borrarFrecuenciaTipo(FrecuenciaTipo obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public FrecuenciaTipo buscarUno(String valor, char... estatus);
	
}
