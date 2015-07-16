package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Endoscopia;

public interface IServicioEndoscopia {
	
	public void guardarEndoscopia(Endoscopia obj);

	public void borrarEndoscopia(Endoscopia obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Endoscopia buscarUno(String valor, char... estatus);

}
