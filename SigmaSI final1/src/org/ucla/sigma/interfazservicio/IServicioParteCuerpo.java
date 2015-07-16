package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.ParteCuerpo;

public interface IServicioParteCuerpo {
	
	public void guardarParteCuerpo(ParteCuerpo obj);

	public void borrarParteCuerpo(ParteCuerpo obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public ParteCuerpo buscarUno(String valor, char... estatus);

}
