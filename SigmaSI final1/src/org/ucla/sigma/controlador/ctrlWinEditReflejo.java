package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Reflejo;
import org.ucla.sigma.servicio.ServicioReflejo;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditReflejo extends GenericForwardComposer {

	private Window winEditReflejo;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;
	

	// ----------------------------------------------------------------------------------------------------

	private ServicioReflejo servicioReflejo;
	private ctrlWinReflejo ctrlwinreflejo;

	// ----------------------------------------------------------------------------------------------------

	private Reflejo reflejo;

	// ----------------------------------------------------------------------------------------------------


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

	

	public void setCtrlwinreflejo(ctrlWinReflejo ctrlwinreflejo) {
		this.ctrlwinreflejo = ctrlwinreflejo;
	}

	
	public void setWinEditReflejo(Window winEditReflejo) {
		this.winEditReflejo = winEditReflejo;
	}

	
	public ServicioReflejo getServicioReflejo() {
		return servicioReflejo;
	}

	public void setServicioReflejo(ServicioReflejo servicioReflejo) {
		this.servicioReflejo = servicioReflejo;
	}

	public Reflejo getReflejo() {
		return reflejo;
	}

	public void setReflejo(Reflejo reflejo) {
		this.reflejo = reflejo;
	}

	public Window getWinEditReflejo() {
		return winEditReflejo;
	}

	public ctrlWinReflejo getCtrlwinreflejo() {
		return ctrlwinreflejo;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditReflejo.setAttribute(comp.getId() + "ctrl", this);
		servicioReflejo = (ServicioReflejo) SpringUtil
				.getBean("beanServicioReflejo");
		reflejo = new Reflejo();
		ctrlwinreflejo = (ctrlWinReflejo) arg.get("ctrlWinReflejo");
		if (!(arg.get("objeto") == null)) {
			reflejo = (Reflejo) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinreflejo.recargar();
		ctrlwinreflejo.apagarBotones();
		this.winEditReflejo.detach();
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
		}  else {
			Reflejo reflejoTemp = servicioReflejo.buscarUno(reflejo.getNombre());
			if (reflejoTemp != null) {
				reflejo.setId(reflejoTemp.getId());
			}

			try {
				servicioReflejo.guardarReflejo(reflejo);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinreflejo.recargar();
						ctrlwinreflejo.apagarBotones();
						winEditReflejo.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditReflejo() {
		ctrlwinreflejo.apagarBotones();
		this.winEditReflejo.detach();
	}
}
