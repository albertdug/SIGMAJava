/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Pulpejos;
import org.ucla.sigma.servicio.ServicioPulpejos;
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
public class ctrlWinEditPulpejos extends GenericForwardComposer {

	private Window winEditPulpejos;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioPulpejos servicioPulpejos;
	private ctrlWinPulpejos ctrlwinpulpejos;
	private Pulpejos pulpejos;
	
	
	// ----------------------------------------------------------------------------------------------------


	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditPulpejos() {
		return winEditPulpejos;
	}

	public void setWinEditPulpejos(Window winEditPulpejos) {
		this.winEditPulpejos = winEditPulpejos;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioPulpejos getServicioPulpejos() {
		return servicioPulpejos;
	}

	public void setServicioPulpejos(ServicioPulpejos servicioPulpejos) {
		this.servicioPulpejos = servicioPulpejos;
	}

	public ctrlWinPulpejos getCtrlwinpulpejos() {
		return ctrlwinpulpejos;
	}

	public void setCtrlwinpulpejos(ctrlWinPulpejos ctrlwinpulpejos) {
		this.ctrlwinpulpejos = ctrlwinpulpejos;
	}

	public Pulpejos getPulpejos() {
		return pulpejos;
	}

	public void setPulpejos(Pulpejos pulpejos) {
		this.pulpejos = pulpejos;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditPulpejos.setAttribute(comp.getId() + "ctrl", this);
		servicioPulpejos = (ServicioPulpejos) SpringUtil
				.getBean("beanServicioPulpejos");
		pulpejos = new Pulpejos();
		ctrlwinpulpejos = (ctrlWinPulpejos) arg.get("ctrlWinPulpejos");
		if (!(arg.get("objeto") == null)) {
			pulpejos = (Pulpejos) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinpulpejos.recargar();
		ctrlwinpulpejos.apagarBotones();
		this.winEditPulpejos.detach();
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
			Pulpejos pulpejosTemp = servicioPulpejos.buscarUno(pulpejos.getNombre());
			if (pulpejosTemp != null) {
				pulpejos.setId(pulpejosTemp.getId());
			}

			try {
				servicioPulpejos.guardarPulpejos(pulpejos);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinpulpejos.recargar();
						ctrlwinpulpejos.apagarBotones();
						winEditPulpejos.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditPulpejos() {
		ctrlwinpulpejos.apagarBotones();
		this.winEditPulpejos.detach();
	}



}
