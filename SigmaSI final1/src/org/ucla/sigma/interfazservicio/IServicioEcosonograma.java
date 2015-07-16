package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Ecosonograma;

public interface IServicioEcosonograma {
	
	public void guardarEcosonograma(Ecosonograma obj);

	public void borrarEcosonograma(Ecosonograma obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Ecosonograma buscarUno(String valor, char... estatus);

}
