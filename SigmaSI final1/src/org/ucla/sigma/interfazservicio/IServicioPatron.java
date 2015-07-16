package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Patron;


public interface IServicioPatron {
	
	public void guardarPatron(Patron obj);

	public void borrarPatron(Patron obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Patron buscarUno(String valor, char... estatus);

}