package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Publicidad;

public interface IServicioPublicidad {

	Publicidad getRand(char[] estatus);

	Publicidad buscarUnoPorId(int id, char[] estatus);

	void guardarPublicidad(Publicidad obj);

	Publicidad buscarUno(String valor, char[] estatus);

	List buscarCoincidencias(String valor, char[] estatus);

	List buscarTodos(char[] estatus);

	void borrarPublicidad(Publicidad obj);

	

}
