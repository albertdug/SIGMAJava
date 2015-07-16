package org.ucla.sigma.componentszk;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;

/**
 * Implementa EventListener para ser utilizado como evento de la clase
 * MensajeEmergente
 */
public class MensajeListener implements EventListener {

	/**
	 * Esta funcion se sobre-escribe si se desea efectuar alguna accion al
	 * aceptar
	 */
	public void alAceptar() {
	}

	/**
	 * Esta funcion se sobre-escribe si se desea efectuar alguna accion al
	 * cancelar
	 */
	public void alCancelar() {
	}

	/**
	 * Esta funcion se sobre-escribe si se desea efectuar alguna accion al
	 * destruir el mensaje emergente, esta siempre se ejecuta
	 */
	public void alDestruir() {
	}

	@Override
	public void onEvent(Event e) throws Exception {
		if (Messagebox.ON_YES.equals(e.getName())
				|| Messagebox.ON_OK.equals(e.getName())) {
			alAceptar();
		} else if (Messagebox.ON_NO.equals(e.getName())
				|| Messagebox.ON_CANCEL.equals(e.getName())
				|| Messagebox.ON_ABORT.equals(e.getName())
				|| Messagebox.ON_IGNORE.equals(e.getName())) {
			alCancelar();
		}
		alDestruir();
	}

}
