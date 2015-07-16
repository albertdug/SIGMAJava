package org.ucla.sigma.interfazservicio;

import java.util.Date;
import java.util.List;

import org.ucla.sigma.modelo.Notificacion;


public interface IServicioNotificacion {
	
	public List buscarTodos(char... estatus);
	
	public Notificacion buscarUno(String valor, char... estatus);

	void guardarNotificacion(Notificacion obj);

	List buscarCoincidenciasFechas(Date fechainicio, Date fechafinal,
			char[] estatus);

	void notificacionLeida(Notificacion obj);
	
}
