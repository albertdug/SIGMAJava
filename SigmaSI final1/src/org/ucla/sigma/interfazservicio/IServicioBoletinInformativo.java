package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.BoletinInformativo;

public interface IServicioBoletinInformativo {

	public void guardarBoletinInformativo(BoletinInformativo obj);

	public BoletinInformativo getRand(char... estatus);

	public BoletinInformativo buscarUnoPorId(int id, char... estatus);

	public void borrarBoletinInformativo(BoletinInformativo obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public BoletinInformativo buscarUno(String valor, char... estatus);
	
}
