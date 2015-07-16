package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Ganglio;

public interface IServicioGanglio {
	
	public void guardarGanglio(Ganglio obj);

	public void borrarGanglio(Ganglio obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Ganglio buscarUno(String valor, char... estatus);

}
