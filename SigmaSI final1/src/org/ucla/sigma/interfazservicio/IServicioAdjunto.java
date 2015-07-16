package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Adjunto;

public interface IServicioAdjunto {

	Adjunto buscarUnoPorId(int id, char[] estatus);

	Adjunto buscarUno(String valor, char[] estatus);

	List buscarCoincidencias(String valor, char[] estatus);

	List buscarTodos(char[] estatus);

	void borrarAdjunto(Adjunto obj);

	void guardarAdjunto(Adjunto obj);
	
}
