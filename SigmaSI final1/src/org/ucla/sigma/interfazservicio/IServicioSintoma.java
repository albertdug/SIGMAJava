package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Sintoma;


public interface IServicioSintoma {

	public void guardarSintoma(Sintoma obj);

	public void borrarSintoma(Sintoma obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Sintoma buscarUno(String valor, char... estatus);

}
