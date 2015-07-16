package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Cojera;

public interface IServicioCojera {
	
	public void guardarCojera(Cojera obj);

	public void borrarCojera(Cojera obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Cojera buscarUno(String valor, char... estatus);

}
