package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Ecocardiograma;
import org.ucla.sigma.servicio.ServicioEcocardiograma;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditEcocardiograma extends GenericForwardComposer {

	private Window winEditEcocardiograma;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioEcocardiograma servicioEcocardiograma;
	private ctrlWinEcocardiograma ctrlwinecocardiograma;

	// ----------------------------------------------------------------------------------------------------

	private Ecocardiograma ecocardiograma;

	// ----------------------------------------------------------------------------------------------------

	public ServicioEcocardiograma getServicioEcocardiograma() {
		return servicioEcocardiograma;
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

	public void setServicioEcocardiograma(
			ServicioEcocardiograma servicioEcocardiograma) {
		this.servicioEcocardiograma = servicioEcocardiograma;
	}

	public ctrlWinEcocardiograma getCtrlwinecocardiograma() {
		return ctrlwinecocardiograma;
	}

	public void setCtrlwinecocardiograma(
			ctrlWinEcocardiograma ctrlwinecocardiograma) {
		this.ctrlwinecocardiograma = ctrlwinecocardiograma;
	}

	public Window getWinEditEcocardiograma() {
		return winEditEcocardiograma;
	}

	public void setWinEditEcocardiograma(Window winEditEcocardiograma) {
		this.winEditEcocardiograma = winEditEcocardiograma;
	}

	public Ecocardiograma getEcocardiograma() {
		return ecocardiograma;
	}

	public void setEcocardiograma(Ecocardiograma ecocardiograma) {
		this.ecocardiograma = ecocardiograma;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditEcocardiograma.setAttribute(comp.getId() + "ctrl", this);
		servicioEcocardiograma = (ServicioEcocardiograma) SpringUtil
				.getBean("beanServicioEcocardiograma");
		ecocardiograma = new Ecocardiograma();
		ctrlwinecocardiograma = (ctrlWinEcocardiograma) arg
				.get("ctrlWinEcocardiograma");
		if (!(arg.get("objeto") == null)) {
			ecocardiograma = (Ecocardiograma) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinecocardiograma.recargar();
		ctrlwinecocardiograma.apagarBotones();
		this.winEditEcocardiograma.detach();
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
			Ecocardiograma ecocardiogramaTemp = servicioEcocardiograma
					.buscarUno(ecocardiograma.getNombre());
			if (ecocardiogramaTemp != null) {
				ecocardiograma.setId(ecocardiogramaTemp.getId());
			}

			try {
				servicioEcocardiograma.guardarEcocardiograma(ecocardiograma);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinecocardiograma.recargar();
						ctrlwinecocardiograma.apagarBotones();
						winEditEcocardiograma.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}
	}

	public void onClose$winEditEcocardiograma() {
		ctrlwinecocardiograma.apagarBotones();
		this.winEditEcocardiograma.detach();
	}
}
