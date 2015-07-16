package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Ataxia;

public interface IServicioAtaxia {
	
	public void guardarAtaxia(Ataxia obj);

	public void borrarAtaxia(Ataxia obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Ataxia buscarUno(String valor, char... estatus);

}
