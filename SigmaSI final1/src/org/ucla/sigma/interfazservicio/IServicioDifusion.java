package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Difusion;

public interface IServicioDifusion {
	
	public void guardarDifusion(Difusion obj);

	public void borrarDifusion(Difusion obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Difusion buscarUno(String valor, char... estatus);
	
	
}
