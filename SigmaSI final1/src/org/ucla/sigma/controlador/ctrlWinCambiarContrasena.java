/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.components.HelperString;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.SessionAdministrator;
import org.ucla.sigma.modelo.Usuario;
import org.ucla.sigma.servicio.ServicioArea;
import org.ucla.sigma.servicio.ServicioUsuario;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author JP
 * 
 */
public class ctrlWinCambiarContrasena extends GenericForwardComposer {

	private Window winCambiarContrasena;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNuevaContrasena2;
	private Textbox txtNuevaContrasena;
	private Textbox txtContrasena;
	private Textbox txtUsuario;
	private Textbox txtLogin;
	private Usuario usuario;
	private ServicioUsuario servicioUsuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winCambiarContrasena.setAttribute(comp.getId() + "ctrl", this);
		servicioUsuario = (ServicioUsuario) SpringUtil
				.getBean("beanServicioUsuario");
		usuario = SessionAdministrator.getLoggedUsuario();
		txtUsuario.setValue(usuario.getPersona().getNombre() + " "
				+ usuario.getPersona().getApellido());
		txtLogin.setValue(usuario.getLogin());
	}

	public void onClick$btnGuardar() {

		if (HelperString.isEmpty(txtContrasena.getValue())) {
			MensajeEmergente.mostrar("NOEMPTY", "\nContraseña Actual",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtContrasena.setFocus(true);
						}
					});
		} else if (!txtContrasena.getValue().equals(usuario.getPassword())) {
			MensajeEmergente.mostrar("ERRPASS", "\nContraseña Actual",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtContrasena.setFocus(true);
						}
					});
		} else if (HelperString.isEmpty(txtNuevaContrasena.getValue())) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNueva Contraseña",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtNuevaContrasena.setFocus(true);
						}
					});
		} else if (HelperString.isEmpty(txtNuevaContrasena2.getValue())) {
			MensajeEmergente.mostrar("NOEMPTY",
					"\nReescribir Nueva Contraseña", new MensajeListener() {
						@Override
						public void alDestruir() {
							txtNuevaContrasena2.setFocus(true);
						}
					});
		} else if (!txtNuevaContrasena.getValue().equals(
				txtNuevaContrasena2.getValue())) {
			MensajeEmergente.mostrar("NOEQPASS", new MensajeListener() {
				@Override
				public void alDestruir() {
					txtNuevaContrasena.setFocus(true);
				}
			});
		} else {

			try {
				usuario.setPassword(txtNuevaContrasena.getValue());
				servicioUsuario.guardarUsuario(usuario);
				MensajeEmergente.mostrar("REGINSERT");
				onClose$winCambiarContrasena();
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}
	}

	public void onClick$btnCancelar() {
		onClose$winCambiarContrasena();
	}

	public void onClose$winCambiarContrasena() {
		winCambiarContrasena.detach();
	}
}
