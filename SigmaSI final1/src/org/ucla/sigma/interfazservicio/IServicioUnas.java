package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Unas;

public interface IServicioUnas {
	
	public void guardarUnas(Unas obj);

	public void borrarUnas(Unas obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Unas buscarUno(String valor, char... estatus);

}
