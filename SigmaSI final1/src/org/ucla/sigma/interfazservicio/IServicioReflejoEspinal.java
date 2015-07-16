package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.ReflejoEspinal;

public interface IServicioReflejoEspinal {
	
	public void guardarReflejoEspinal(ReflejoEspinal obj);

	public void borrarReflejoEspinal(ReflejoEspinal obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public ReflejoEspinal buscarUno(String valor, char... estatus);

}
