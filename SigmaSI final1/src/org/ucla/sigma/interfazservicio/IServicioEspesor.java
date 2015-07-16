package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Espesor;

public interface IServicioEspesor {
	
	public void guardarEspesor(Espesor obj);

	public void borrarEspesor(Espesor obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Espesor buscarUno(String valor, char... estatus);

}


