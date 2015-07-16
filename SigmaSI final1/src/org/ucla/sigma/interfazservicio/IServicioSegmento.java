package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Auscultacion;
import org.ucla.sigma.modelo.Segmento;


public interface IServicioSegmento{

	public void guardarSegmento(Segmento obj);

	public void borrarSegmento(Segmento obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Segmento buscarUno(String valor, char... estatus);

}
