package org.ucla.sigma.componentszk;

import org.ucla.sigma.modelo.Usuario;
import org.zkoss.zk.ui.Executions;

public abstract class SessionAdministrator {

	private static final String sessionName = "sigmaSession";

	/**
	 * Esta funcion retorna un usuario
	 * 
	 * @return Usuario de la sesion, null si no se ha iniciado sesion
	 */
	public static Usuario getLoggedUsuario() {
		return (Usuario) Executions.getCurrent().getSession()
				.getAttribute(sessionName);
	}

	/**
	 * Esta funcion guarda en la sesion actual un usuario
	 */
	public static void setSessionUsuario(Usuario usuario) {
		Executions.getCurrent().getSession().setAttribute(sessionName, usuario);
	}

	/**
	 * Esta funcion destruye la sesion actual
	 */
	public static void sessionDestroy() {
		Executions.getCurrent().getSession().invalidate();
	}
}
