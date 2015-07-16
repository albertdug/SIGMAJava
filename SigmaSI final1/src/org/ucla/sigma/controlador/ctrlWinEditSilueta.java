package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Silueta;
import org.ucla.sigma.servicio.ServicioSilueta;
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
public class ctrlWinEditSilueta extends GenericForwardComposer {

	private Window winEditSilueta;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioSilueta servicioSilueta;
	private ctrlWinSilueta ctrlwinsilueta;

	// ----------------------------------------------------------------------------------------------------

	private Silueta silueta;

	// ----------------------------------------------------------------------------------------------------

	public ServicioSilueta getServicioSilueta() {
		return servicioSilueta;
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

	public void setServicioSilueta(ServicioSilueta servicioSilueta) {
		this.servicioSilueta = servicioSilueta;
	}

	public ctrlWinSilueta getCtrlwinsilueta() {
		return ctrlwinsilueta;
	}

	public void setCtrlwinsilueta(ctrlWinSilueta ctrlwinsilueta) {
		this.ctrlwinsilueta = ctrlwinsilueta;
	}

	public Window getWinEditSilueta() {
		return winEditSilueta;
	}

	public void setWinEditSilueta(Window winEditSilueta) {
		this.winEditSilueta = winEditSilueta;
	}

	public Silueta getSilueta() {
		return silueta;
	}

	public void setSilueta(Silueta silueta) {
		this.silueta = silueta;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditSilueta.setAttribute(comp.getId() + "ctrl", this);
		servicioSilueta = (ServicioSilueta) SpringUtil
				.getBean("beanServicioSilueta");
		silueta = new Silueta();
		ctrlwinsilueta = (ctrlWinSilueta) arg.get("ctrlWinSilueta");
		if (!(arg.get("objeto") == null)) {
			silueta = (Silueta) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinsilueta.apagarBotones();
		ctrlwinsilueta.recargar();
		this.winEditSilueta.detach();
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
			Silueta siluetaTemp = servicioSilueta
					.buscarUno(silueta.getNombre());
			if (siluetaTemp != null) {
				silueta.setId(siluetaTemp.getId());
			}

			try {
				servicioSilueta.guardarSilueta(silueta);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinsilueta.recargar();
						ctrlwinsilueta.apagarBotones();
						winEditSilueta.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditEspecie() {
		ctrlwinsilueta.apagarBotones();
		this.winEditSilueta.detach();
	}

}
