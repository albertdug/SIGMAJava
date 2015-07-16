package org.ucla.sigma.interfazservicio;

import java.util.Date;
import java.util.List;
import org.ucla.sigma.modelo.Suscripcion;

public interface IServicioSuscripcion {
	
	//public void guardarSuscripcion(Suscripcion obj);

	public void borrarSuscripcion(Suscripcion obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);
	
	public List buscarCoincidenciasFecha(Date fechainicio, Date fechafinal, char... estatus);

	public Suscripcion buscarUno(String valor, char... estatus);

	void guardarSuscripcion(Suscripcion obj);

	Suscripcion buscarUnoCorreo(String valor);

}
