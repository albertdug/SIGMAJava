package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Examen;

public interface IServicioExamen {
	
	public void guardarExamen(Examen obj);

	public void borrarExamen(Examen obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Examen buscarUno(String valor, char... estatus);
	
}

