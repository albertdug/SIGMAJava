package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Dosis;

public interface IServicioDosis {
	
	public void guardarDosis(Dosis obj);

	public void borrarDosis(Dosis obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Dosis buscarUno(String valor, char... estatus);

}


