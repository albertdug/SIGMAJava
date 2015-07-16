package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.MembranaMucosa;

public interface IServicioMembranaMucosa {
	
	public void guardarMembranaMucosa(MembranaMucosa obj);

	public void borrarMembranaMucosa(MembranaMucosa obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public MembranaMucosa buscarUno(String valor, char... estatus);

}