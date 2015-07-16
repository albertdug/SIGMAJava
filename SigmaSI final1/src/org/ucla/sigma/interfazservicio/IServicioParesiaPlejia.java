package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.ParesiaPlejia;

public interface IServicioParesiaPlejia {
	
	public void guardarParesiaPlejia(ParesiaPlejia obj);

	public void borrarParesiaPlejia(ParesiaPlejia obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public ParesiaPlejia buscarUno(String valor, char... estatus);

}
