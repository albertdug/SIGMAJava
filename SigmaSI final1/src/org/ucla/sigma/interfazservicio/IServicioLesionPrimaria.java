package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.LesionPrimaria;

public interface IServicioLesionPrimaria {
	
	public void guardarLesionPrimaria(LesionPrimaria obj);

	public void borrarLesionPrimaria(LesionPrimaria obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public LesionPrimaria buscarUno(String valor, char... estatus);

}
