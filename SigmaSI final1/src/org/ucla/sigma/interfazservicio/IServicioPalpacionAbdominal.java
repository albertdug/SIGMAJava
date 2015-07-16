package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.PalpacionAbdominal;

public interface IServicioPalpacionAbdominal {
	
	public void guardarPalpacionAbdominal(PalpacionAbdominal obj);

	public void borrarPalpacionAbdominal(PalpacionAbdominal obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public PalpacionAbdominal buscarUno(String valor, char... estatus);

}
