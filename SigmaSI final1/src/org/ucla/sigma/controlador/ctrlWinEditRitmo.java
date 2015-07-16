package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Ritmo;
import org.ucla.sigma.servicio.ServicioRitmo;
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
public class ctrlWinEditRitmo extends GenericForwardComposer {

	private Window winEditRitmo;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioRitmo servicioRitmo;
	private ctrlWinRitmo ctrlwinritmo;

	// ----------------------------------------------------------------------------------------------------

	private Ritmo ritmo;

	// ----------------------------------------------------------------------------------------------------

	public ServicioRitmo getServicioRitmo() {
		return servicioRitmo;
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

	public void setServicioRitmo(ServicioRitmo servicioRitmo) {
		this.servicioRitmo = servicioRitmo;
	}

	public ctrlWinRitmo getCtrlwinritmo() {
		return ctrlwinritmo;
	}

	public void setCtrlwinritmo(ctrlWinRitmo ctrlwinritmo) {
		this.ctrlwinritmo = ctrlwinritmo;
	}

	public Window getWinEditRitmo() {
		return winEditRitmo;
	}

	public void setWinEditRitmo(Window winEditRitmo) {
		this.winEditRitmo = winEditRitmo;
	}

	public Ritmo getRitmo() {
		return ritmo;
	}

	public void setRitmo(Ritmo ritmo) {
		this.ritmo = ritmo;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditRitmo.setAttribute(comp.getId() + "ctrl", this);
		servicioRitmo = (ServicioRitmo) SpringUtil.getBean("beanServicioRitmo");
		ritmo = new Ritmo();
		ctrlwinritmo = (ctrlWinRitmo) arg.get("ctrlWinRitmo");
		if (!(arg.get("objeto") == null)) {
			ritmo = (Ritmo) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinritmo.apagarBotones();
		ctrlwinritmo.recargar();
		this.winEditRitmo.detach();
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
			Ritmo ritmoTemp = servicioRitmo.buscarUno(ritmo.getNombre());
			if (ritmoTemp != null) {
				ritmo.setId(ritmoTemp.getId());
			}

			try {
				servicioRitmo.guardarRitmo(ritmo);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinritmo.recargar();
						ctrlwinritmo.apagarBotones();
						winEditRitmo.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditEspecie() {
		ctrlwinritmo.apagarBotones();
		this.winEditRitmo.detach();
	}

}
