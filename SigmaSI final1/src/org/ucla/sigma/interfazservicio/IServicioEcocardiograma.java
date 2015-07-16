package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Ecocardiograma;

public interface IServicioEcocardiograma {
	
	public void guardarEcocardiograma(Ecocardiograma obj);

	public void borrarEcocardiograma(Ecocardiograma obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Ecocardiograma buscarUno(String valor, char... estatus);

}
