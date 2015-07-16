package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Imagen;

public interface IServicioImagen {
	
	public void guardarImagen(Imagen obj);

	public void borrarImagen(Imagen obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Imagen buscarUno(String valor, char... estatus);

	public Imagen buscarUnoPorId(int valor, char[] estatus);

	
}
