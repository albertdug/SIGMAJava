package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Auscultacion;


public interface IServicioAuscultacion {

	public void guardarAuscultacion(Auscultacion obj);

	public void borrarAuscultacion(Auscultacion obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Auscultacion buscarUno(String valor, char... estatus);

}
