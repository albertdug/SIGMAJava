package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.Ataxia;
import org.ucla.sigma.servicio.ServicioAtaxia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditAtaxia extends GenericForwardComposer {

	private Window winEditAtaxia;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioAtaxia servicioAtaxia;
	private ctrlWinAtaxia ctrlwinataxia;

	// ----------------------------------------------------------------------------------------------------

	private Ataxia ataxia;

	// ----------------------------------------------------------------------------------------------------

	public ServicioAtaxia getServicioAtaxia() {
		return servicioAtaxia;
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

	public void setServicioAtaxia(ServicioAtaxia servicioAtaxia) {
		this.servicioAtaxia = servicioAtaxia;
	}

	public ctrlWinAtaxia getCtrlwinAtaxia() {
		return ctrlwinataxia;
	}

	public void setCtrlwinataxia(ctrlWinAtaxia ctrlwinataxia) {
		this.ctrlwinataxia = ctrlwinataxia;
	}

	public Window getWinEditAtaxia() {
		return winEditAtaxia;
	}

	public void setWinEditAtaxia(Window winEditAtaxia) {
		this.winEditAtaxia = winEditAtaxia;
	}

	public Ataxia getAtaxia() {
		return ataxia;
	}

	public void setAtaxia(Ataxia ataxia) {
		this.ataxia = ataxia;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditAtaxia.setAttribute(comp.getId() + "ctrl", this);
		servicioAtaxia = (ServicioAtaxia) SpringUtil
				.getBean("beanServicioAtaxia");
		ataxia = new Ataxia();
		ctrlwinataxia = (ctrlWinAtaxia) arg.get("ctrlWinAtaxia");
		if (!(arg.get("objeto") == null)) {
			ataxia = (Ataxia) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinataxia.apagarBotones();
		this.winEditAtaxia.detach();
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
			Ataxia ataxiaTemp = servicioAtaxia
					.buscarUno(ataxia.getNombre());
			if (ataxiaTemp != null) {
				ataxia.setId(ataxiaTemp.getId());
			}

			try {
				servicioAtaxia.guardarAtaxia(ataxia);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinataxia.recargar();
						ctrlwinataxia.apagarBotones();
						winEditAtaxia.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditAtaxia() {
		ctrlwinataxia.apagarBotones();
		this.winEditAtaxia.detach();
	}
}
