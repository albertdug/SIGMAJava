package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Funcion;

public interface IServicioFuncion {
	public void guardarFuncion(Funcion obj);

	public void borrarFuncion(Funcion obj);

	public List buscarTodos(char... estatus);
	
	public List buscarPadres();

	public List buscarCoincidencias(String valor, char... estatus);

	public Funcion buscarUno(String valor, char... estatus);

	public List buscarFunciones();

}
