/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.components.HelperString;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.SessionAdministrator;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.Notificacion;
import org.ucla.sigma.modelo.Respuesta;
import org.ucla.sigma.servicio.ServicioCiudad;
import org.ucla.sigma.servicio.ServicioNotificacion;
import org.ucla.sigma.servicio.ServicioRespuesta;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.sun.org.apache.xml.internal.security.utils.HelperNodeList;

/**
 * @author JP
 * 
 */
public class ctrlWinResponder extends GenericForwardComposer {

	private Window winResponder;
	private Textbox txtTexto;
	private Textbox txtTelefono;
	private Textbox txtCorreo;
	private Textbox txtApellido;
	private Textbox txtNombre;
	private Textbox txtRespuesta;
	private Textbox txtUsuario;
	private Button btnCancelar;
	private Button btnGuardar;
	private Datebox dbCreacion;
	private Datebox dbCreacionRespuesta;

	private ServicioNotificacion servicioNotificacion;
	private ServicioRespuesta servicioRespuesta;
	private Notificacion notificacion;
	private ctrlWinNotificacion ctrlWinNotificacion;
	private Respuesta respuesta;

	public Notificacion getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(Notificacion notificacion) {
		this.notificacion = notificacion;
	}

	public Respuesta getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Respuesta respuesta) {
		this.respuesta = respuesta;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winResponder.setAttribute(comp.getId() + "ctrl", this);
		servicioNotificacion = (ServicioNotificacion) SpringUtil
				.getBean("beanServicioNotificacion");
		servicioRespuesta = (ServicioRespuesta) SpringUtil
				.getBean("beanServicioRespuesta");
		notificacion = new Notificacion();
		notificacion.setCreacion(HelperDate.now());
		respuesta = new Respuesta();
		respuesta.setUsuario(SessionAdministrator.getLoggedUsuario());
		respuesta.setCreacion(HelperDate.now());
		ctrlWinNotificacion = (ctrlWinNotificacion) arg
				.get("ctrlWinNotificacion");
		if (!(arg.get("objeto") == null)) {
			notificacion = (Notificacion) arg.get("objeto");
			servicioNotificacion.notificacionLeida(notificacion);
			if (!notificacion.getRespuestas().isEmpty()) {
				txtRespuesta.setReadonly(true);
				btnGuardar.setDisabled(true);
				respuesta = (Respuesta) notificacion.getRespuestas().toArray()[0];
			}
		}

	}

	public void onClick$btnCancelar() {
		ctrlWinNotificacion.recargar();
		this.winResponder.detach();
	}

	public void onClick$btnGuardar() {
		respuesta.setCreacion(HelperDate.now());
		respuesta.setUsuario(SessionAdministrator.getLoggedUsuario());
		respuesta.setNotificacion(notificacion);
		
		if(HelperString.isEmpty(txtRespuesta.getValue())){
			MensajeEmergente.mostrar("NOEMPTY", "\nRespuesta",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtRespuesta.setFocus(true);
						}
					});
		}else{
			try {//	('MSJSEND','Mensaje enviado exitosamente','INFORMACION'),
				servicioRespuesta.guardarRespuesta(respuesta);
				MensajeEmergente.mostrar("MSJSEND", new MensajeListener() {
					@Override
					public void alAceptar() {
						btnGuardar.setDisabled(true);
						txtRespuesta.setReadonly(true);
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}
		servicioRespuesta.guardarRespuesta(respuesta);
		
	}

	public void onClose$winResponder() {
		ctrlWinNotificacion.recargar();
		this.winResponder.detach();
	}

}
