package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.LesionSecundaria;

public interface IServicioLesionSecundaria {
	
	public void guardarLesionSecundaria(LesionSecundaria obj);

	public void borrarLesionSecundaria(LesionSecundaria obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public LesionSecundaria buscarUno(String valor, char... estatus);

}
