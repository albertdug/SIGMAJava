package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Auscultacion;
import org.ucla.sigma.modelo.Pulmonar;


public interface IServicioPulmonar {

	public Pulmonar buscarUno(String valor, char... estatus);

	public void borrarPulmonar(Pulmonar obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public void guardarPulmonar(Pulmonar obj);

}
