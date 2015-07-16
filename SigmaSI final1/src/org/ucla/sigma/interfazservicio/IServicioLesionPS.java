package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.LesionPS;

public interface IServicioLesionPS {
	
	public void guardarLesionPS(LesionPS obj);

	public void borrarLesionPS(LesionPS obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public LesionPS buscarUno(String valor, char... estatus);

}