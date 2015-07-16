package org.ucla.sigma.componentszk;

import java.util.Iterator;

import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.Mensaje;
import org.ucla.sigma.servicio.ServicioMensaje;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;

/**
 * Clase que envuelve Messagebox, extrae los mensajes desde la base de datos
 */
public abstract class MensajeEmergente {

	private static ServicioMensaje servicioMensaje;

	/**
	 * Muestra un mensaje
	 * 
	 * @param codigo
	 *            clave primaria del mensaje que se desea mostrar
	 */
	public static void mostrar(String codigo) {
		mensaje(codigo, "", null);
	}

	/**
	 * Muestra un mensaje mas algun texto extra
	 * 
	 * @param codigo
	 *            clave primaria del mensaje que se desea mostrar
	 * @param textoExtra
	 *            texto adicional
	 */
	public static void mostrar(String codigo, String textoExtra) {
		mensaje(codigo, textoExtra, null);
	}

	/**
	 * Muestra un mensaje y personaliza los listeners
	 * 
	 * @param codigo
	 *            clave primaria del mensaje que se desea mostrar
	 * @param listener
	 *            MensajeListener
	 */
	public static void mostrar(String codigo, MensajeListener listener) {
		mensaje(codigo, "", listener);
	}

	/**
	 * Muestra un mensaje mas algun texto extra y personaliza los listeners
	 * 
	 * @param codigo
	 *            clave primaria del mensaje que se desea mostrar
	 * @param textoExtra
	 *            texto adicional
	 * @param listener
	 *            MensajeListener
	 */
	public static void mostrar(String codigo, String textoExtra,
			MensajeListener listener) {
		mensaje(codigo, textoExtra, listener);
	}

	private static void mensaje(String codigo, String textoExtra,
			MensajeListener listener) {
		try {
			int botones = 0;
			servicioMensaje = (ServicioMensaje) SpringUtil
					.getBean("beanServicioMensaje");

			Mensaje mensaje = servicioMensaje.buscarUno(codigo);
			if (mensaje == null) {
				Messagebox.show(
						"Error grave, No se puede mostrar mensaje deseado",
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			for (Iterator<Boton> i = mensaje.getTipoMensaje().getBotons()
					.iterator(); i.hasNext();) {
				botones |= i.next().getValor();
			}

			Messagebox.show(mensaje.getTexto() + textoExtra, mensaje
					.getTipoMensaje().getTitulo(), botones, mensaje
					.getTipoMensaje().getIcono(), listener);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
