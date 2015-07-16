package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.Estado;

public interface IServicioCiudad {

	public void guardarCiudad(Ciudad obj);

	public void borrarCiudad(Ciudad obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Ciudad buscarUno(String valor, char... estatus);

	List buscarEstadosAsociados(Estado valor, char[] estatus);

}
