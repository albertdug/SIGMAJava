package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Postura;

public interface IServicioPostura {
	
	public void guardarPostura(Postura obj);

	public void borrarPostura(Postura obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Postura buscarUno(String valor, char... estatus);


}
