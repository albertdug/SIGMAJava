package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Pulpejos;

public interface IServicioPulpejos {
	
	public void guardarPulpejos(Pulpejos obj);

	public void borrarPulpejos(Pulpejos obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Pulpejos buscarUno(String valor, char... estatus);

}

