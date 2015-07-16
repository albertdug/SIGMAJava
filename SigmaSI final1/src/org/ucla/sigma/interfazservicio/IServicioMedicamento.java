package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Medicamento;

public interface IServicioMedicamento {
	
	public void guardarMedicamento(Medicamento obj);

	public void borrarMedicamento(Medicamento obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Medicamento buscarUno(String valor, char... estatus);

}
