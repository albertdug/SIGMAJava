package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.Postura;
import org.ucla.sigma.servicio.ServicioPostura;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditPostura extends GenericForwardComposer {

	private Window winEditPostura;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioPostura servicioPostura;
	private ctrlWinPostura ctrlwinpostura;

	// ----------------------------------------------------------------------------------------------------

	private Postura postura;

	// ----------------------------------------------------------------------------------------------------

	public ServicioPostura getServicioPostura() {
		return servicioPostura;
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

	public void setServicioPostura(ServicioPostura servicioPostura) {
		this.servicioPostura = servicioPostura;
	}

	public ctrlWinPostura getCtrlwinpostura() {
		return ctrlwinpostura;
	}

	public void setCtrlwinpostura(ctrlWinPostura ctrlwinpostura) {
		this.ctrlwinpostura = ctrlwinpostura;
	}

	public Window getWinEditPostura() {
		return winEditPostura;
	}

	public void setWinEditPostura(Window winEditPostura) {
		this.winEditPostura = winEditPostura;
	}

	public Postura getPostura() {
		return postura;
	}

	public void setPostura(Postura postura) {
		this.postura = postura;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditPostura.setAttribute(comp.getId() + "ctrl", this);
		servicioPostura = (ServicioPostura) SpringUtil
				.getBean("beanServicioPostura");
		postura = new Postura();
		ctrlwinpostura = (ctrlWinPostura) arg.get("ctrlWinPostura");
		if (!(arg.get("objeto") == null)) {
			postura = (Postura) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinpostura.apagarBotones();
		this.winEditPostura.detach();
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
			Postura posturaTemp = servicioPostura
					.buscarUno(postura.getNombre());
			if (posturaTemp != null) {
				postura.setId(posturaTemp.getId());
			}

			try {
				servicioPostura.guardarPostura(postura);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinpostura.recargar();
						ctrlwinpostura.apagarBotones();
						winEditPostura.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditPostura() {
		ctrlwinpostura.apagarBotones();
		this.winEditPostura.detach();
	}
}
