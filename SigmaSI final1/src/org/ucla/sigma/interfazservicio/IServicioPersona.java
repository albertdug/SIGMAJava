package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Persona;

public interface IServicioPersona {
	
	public void guardarPersona(Persona obj);

	public void borrarPersona(Persona obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Persona buscarUno(int valor, char... estatus);

}
