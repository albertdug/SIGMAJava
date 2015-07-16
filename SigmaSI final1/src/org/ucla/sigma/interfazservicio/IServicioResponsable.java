package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Responsable;

public interface IServicioResponsable {
	
	public void guardarResponsable(Responsable obj);

	public void borrarResponsable(Responsable obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(int valor, char... estatus);

	public Responsable buscarUno(int valor, char... estatus);

}
