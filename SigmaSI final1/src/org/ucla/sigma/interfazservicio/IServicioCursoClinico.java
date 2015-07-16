package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.CursoClinico;

public interface IServicioCursoClinico {
	
	public void guardarCursoClinico(CursoClinico obj);

	public void borrarCursoClinico(CursoClinico obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public CursoClinico buscarUno(String valor, char... estatus);

}
