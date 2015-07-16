package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Pulmonar;
import org.ucla.sigma.servicio.ServicioPulmonar;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author Albert
 * 
 */
public class ctrlWinEditPulmonar extends GenericForwardComposer {

	private Window winEditPulmonar;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioPulmonar servicioPulmonar;
	private ctrlWinPulmonar ctrlwinpulmonar;

	// ----------------------------------------------------------------------------------------------------

	private Pulmonar pulmonar;

	// ----------------------------------------------------------------------------------------------------

	public ServicioPulmonar getServicioPulmonar() {
		return servicioPulmonar;
	}

	public Button getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public Button getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(Button btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public void setServicioPulmonar(ServicioPulmonar servicioPulmonar) {
		this.servicioPulmonar = servicioPulmonar;
	}

	public ctrlWinPulmonar getCtrlwinpulmonar() {
		return ctrlwinpulmonar;
	}

	public void setCtrlwinpulmonar(ctrlWinPulmonar ctrlwinpulmonar) {
		this.ctrlwinpulmonar = ctrlwinpulmonar;
	}

	public Window getWinEditPulmonar() {
		return winEditPulmonar;
	}

	public void setWinEditPulmonar(Window winEditPulmonar) {
		this.winEditPulmonar = winEditPulmonar;
	}

	public Pulmonar getPulmonar() {
		return pulmonar;
	}

	public void setPulmonar(Pulmonar pulmonar) {
		this.pulmonar = pulmonar;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditPulmonar.setAttribute(comp.getId() + "ctrl", this);
		servicioPulmonar = (ServicioPulmonar) SpringUtil
				.getBean("beanServicioPulmonar");
		pulmonar = new Pulmonar();
		ctrlwinpulmonar = (ctrlWinPulmonar) arg.get("ctrlWinPulmonar");
		if (!(arg.get("objeto") == null)) {
			pulmonar = (Pulmonar) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinpulmonar.apagarBotones();
		ctrlwinpulmonar.recargar();
		this.winEditPulmonar.detach();
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
			Pulmonar pulmonarTemp = servicioPulmonar.buscarUno(pulmonar
					.getNombre());
			if (pulmonarTemp != null) {
				pulmonar.setId(pulmonarTemp.getId());
			}

			try {
				servicioPulmonar.guardarPulmonar(pulmonar);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinpulmonar.recargar();
						ctrlwinpulmonar.apagarBotones();
						winEditPulmonar.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditEspecie() {
		ctrlwinpulmonar.apagarBotones();
		this.winEditPulmonar.detach();
	}

}
