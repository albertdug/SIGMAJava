package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Auscultacion;
import org.ucla.sigma.modelo.Cardiaca;


public interface IServicioCardiaca {

	public void guardarCardiaca(Cardiaca obj);

	public void borrarCardiaca(Cardiaca obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Cardiaca buscarUno(String valor, char... estatus);

}
