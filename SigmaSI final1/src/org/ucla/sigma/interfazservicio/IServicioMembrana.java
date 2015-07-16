package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Auscultacion;
import org.ucla.sigma.modelo.Membrana;


public interface IServicioMembrana {

	public void guardarMembrana(Membrana obj);

	public void borrarMembrana(Membrana obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Membrana buscarUno(String valor, char... estatus);

}
