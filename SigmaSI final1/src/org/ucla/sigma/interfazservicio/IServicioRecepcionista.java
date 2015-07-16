package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Recepcionista;

public interface IServicioRecepcionista {
	
	public void guardarRecepcionista(Recepcionista obj);

	public void borrarRecepcionista(Recepcionista obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(int valor, char... estatus);

	public Recepcionista buscarUno(int valor, char... estatus);

}
