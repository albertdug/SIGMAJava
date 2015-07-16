/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Unas;
import org.ucla.sigma.servicio.ServicioUnas;
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
public class ctrlWinEditUnas extends GenericForwardComposer {

	private Window winEditUnas;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioUnas servicioUnas;
	private ctrlWinUnas ctrlwinunas;
	private Unas unas;	

	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditUnas() {
		return winEditUnas;
	}

	public void setWinEditUnas(Window winEditUnas) {
		this.winEditUnas = winEditUnas;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioUnas getServicioUnas() {
		return servicioUnas;
	}

	public void setServicioUnas(ServicioUnas servicioUnas) {
		this.servicioUnas = servicioUnas;
	}

	public ctrlWinUnas getCtrlwinunas() {
		return ctrlwinunas;
	}

	public void setCtrlwinunas(ctrlWinUnas ctrlwinunas) {
		this.ctrlwinunas = ctrlwinunas;
	}

	public Unas getUnas() {
		return unas;
	}

	public void setUnas(Unas unas) {
		this.unas = unas;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditUnas.setAttribute(comp.getId() + "ctrl", this);
		servicioUnas = (ServicioUnas) SpringUtil
				.getBean("beanServicioUnas");
		unas = new Unas();
		ctrlwinunas = (ctrlWinUnas) arg.get("ctrlWinUnas");
		if (!(arg.get("objeto") == null)) {
			unas = (Unas) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinunas.recargar();
		ctrlwinunas.apagarBotones();
		this.winEditUnas.detach();
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
			Unas unasTemp = servicioUnas.buscarUno(unas.getNombre());
			if (unasTemp != null) {
				unas.setId(unasTemp.getId());
			}

			try {
				servicioUnas.guardarUnas(unas);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinunas.recargar();
						ctrlwinunas.apagarBotones();
						winEditUnas.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditUnas() {
		ctrlwinunas.apagarBotones();
		this.winEditUnas.detach();
	}



}
