package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Endoscopia;
import org.ucla.sigma.servicio.ServicioEndoscopia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditEndoscopia extends GenericForwardComposer {

	private Window winEditEndoscopia;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioEndoscopia servicioEndoscopia;
	private ctrlWinEndoscopia ctrlwinendoscopia;

	// ----------------------------------------------------------------------------------------------------

	private Endoscopia endoscopia;

	// ----------------------------------------------------------------------------------------------------

	public ServicioEndoscopia getServicioEndoscopia() {
		return servicioEndoscopia;
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

	public void setServicioEndoscopia(ServicioEndoscopia servicioEndoscopia) {
		this.servicioEndoscopia = servicioEndoscopia;
	}

	public ctrlWinEndoscopia getCtrlwinendoscopia() {
		return ctrlwinendoscopia;
	}

	public void setCtrlwinendoscopia(ctrlWinEndoscopia ctrlwinendoscopia) {
		this.ctrlwinendoscopia = ctrlwinendoscopia;
	}

	public Window getWinEditEndoscopia() {
		return winEditEndoscopia;
	}

	public void setWinEditEndoscopia(Window winEditEndoscopia) {
		this.winEditEndoscopia = winEditEndoscopia;
	}

	public Endoscopia getEndoscopia() {
		return endoscopia;
	}

	public void setEndoscopia(Endoscopia endoscopia) {
		this.endoscopia = endoscopia;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditEndoscopia.setAttribute(comp.getId() + "ctrl", this);
		servicioEndoscopia = (ServicioEndoscopia) SpringUtil
				.getBean("beanServicioEndoscopia");
		endoscopia = new Endoscopia();
		ctrlwinendoscopia = (ctrlWinEndoscopia) arg.get("ctrlWinEndoscopia");
		if (!(arg.get("objeto") == null)) {
			endoscopia = (Endoscopia) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinendoscopia.recargar();
		ctrlwinendoscopia.apagarBotones();
		this.winEditEndoscopia.detach();
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

			Endoscopia endoscopiaTemp = servicioEndoscopia.buscarUno(endoscopia
					.getNombre());
			if (endoscopiaTemp != null) {
				endoscopia.setId(endoscopiaTemp.getId());
			}
			System.out.println(endoscopiaTemp);
			System.out.println(endoscopia);
			try {
				servicioEndoscopia.guardarEndoscopia(endoscopia);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinendoscopia.recargar();
						ctrlwinendoscopia.apagarBotones();
						winEditEndoscopia.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditEndoscopia() {
		ctrlwinendoscopia.apagarBotones();
		this.winEditEndoscopia.detach();
	}
}
