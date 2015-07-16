package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.PatronDistribucion;

public interface IServicioPatronDistribucion {
	
	public void guardarPatronDistribucion(PatronDistribucion obj);

	public void borrarPatronDistribucion(PatronDistribucion obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public PatronDistribucion buscarUno(String valor, char... estatus);

}