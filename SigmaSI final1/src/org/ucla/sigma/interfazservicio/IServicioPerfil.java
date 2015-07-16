package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Funcion;
import org.ucla.sigma.modelo.Perfil;

public interface IServicioPerfil {
	public void guardarPerfil(Perfil obj);

	public void borrarPerfil(Perfil obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Perfil buscarUno(String valor, char... estatus);

	List<Funcion> getFunciones(List<Perfil> perfiles);
	
}
