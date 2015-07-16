package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Auscultacion;
import org.ucla.sigma.modelo.Ritmo;


public interface IServicioRitmo {

	public void guardarRitmo(Ritmo obj);

	public void borrarRitmo(Ritmo obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Ritmo buscarUno(String valor, char... estatus);

}
