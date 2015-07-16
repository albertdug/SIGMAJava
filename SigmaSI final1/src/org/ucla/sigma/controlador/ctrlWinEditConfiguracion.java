/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Configuracion;
import org.ucla.sigma.servicio.ServicioConfiguracion;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author jhoan
 *
 */
public class ctrlWinEditConfiguracion extends GenericForwardComposer {

	private Window winEditConfiguracion;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioConfiguracion servicioConfiguracion;
	private ctrlWinConfiguracion ctrlwinconfiguracion;
	private Configuracion configuracion;	

	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditConfiguracion() {
		return winEditConfiguracion;
	}

	public void setWinEditConfiguracion(Window winEditConfiguracion) {
		this.winEditConfiguracion = winEditConfiguracion;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioConfiguracion getServicioConfiguracion() {
		return servicioConfiguracion;
	}

	public void setServicioConfiguracion(ServicioConfiguracion servicioConfiguracion) {
		this.servicioConfiguracion = servicioConfiguracion;
	}

	public ctrlWinConfiguracion getCtrlwinconfiguracion() {
		return ctrlwinconfiguracion;
	}

	public void setCtrlwinconfiguracion(ctrlWinConfiguracion ctrlwinconfiguracion) {
		this.ctrlwinconfiguracion = ctrlwinconfiguracion;
	}

	public Configuracion getConfiguracion() {
		return configuracion;
	}

	public void setConfiguracion(Configuracion configuracion) {
		this.configuracion = configuracion;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditConfiguracion.setAttribute(comp.getId() + "ctrl", this);
		servicioConfiguracion = (ServicioConfiguracion) SpringUtil
				.getBean("beanServicioConfiguracion");
		configuracion = new Configuracion();
		ctrlwinconfiguracion = (ctrlWinConfiguracion) arg.get("ctrlWinConfiguracion");
		if (!(arg.get("objeto") == null)) {
			configuracion = (Configuracion) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinconfiguracion.recargar();
		ctrlwinconfiguracion.apagarBotones();
		this.winEditConfiguracion.detach();
	}

	public void onClick$btnGuardar() {

		if (txtNombre.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNombre",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtNombre.setFocus(true);
						}
					});
		} else {
			Configuracion configuracionTemp = servicioConfiguracion.buscarUno(configuracion.getNombre());
			if (configuracionTemp != null) {
				configuracion.setId(configuracionTemp.getId());
			}

			try {
				servicioConfiguracion.guardarConfiguracion(configuracion);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinconfiguracion.recargar();
						ctrlwinconfiguracion.apagarBotones();
						winEditConfiguracion.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditConfiguracion() {
		ctrlwinconfiguracion.apagarBotones();
		this.winEditConfiguracion.detach();
	}



}