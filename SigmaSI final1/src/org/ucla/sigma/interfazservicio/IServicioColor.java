package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Color;

public interface IServicioColor {
	
	public void guardarColor(Color obj);

	public void borrarColor(Color obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Color buscarUno(String valor, char... estatus);

}
