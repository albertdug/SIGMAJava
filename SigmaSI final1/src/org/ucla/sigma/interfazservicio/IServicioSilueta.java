package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Auscultacion;
import org.ucla.sigma.modelo.Silueta;


public interface IServicioSilueta {

	public void guardarSilueta(Silueta obj);

	public void borrarSilueta(Silueta obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Silueta buscarUno(String valor, char... estatus);

}
