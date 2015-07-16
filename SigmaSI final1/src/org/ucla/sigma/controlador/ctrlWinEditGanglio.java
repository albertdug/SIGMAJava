package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Ganglio;
import org.ucla.sigma.servicio.ServicioGanglio;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditGanglio extends GenericForwardComposer {

	private Window winEditGanglio;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioGanglio servicioGanglio;
	private ctrlWinGanglio ctrlwinganglio;

	// ----------------------------------------------------------------------------------------------------

	private Ganglio ganglio;

	// ----------------------------------------------------------------------------------------------------

	public ServicioGanglio getServicioGanglio() {
		return servicioGanglio;
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

	public void setServicioGanglio(ServicioGanglio servicioGanglio) {
		this.servicioGanglio = servicioGanglio;
	}

	public ctrlWinGanglio getCtrlwinganglio() {
		return ctrlwinganglio;
	}

	public void setCtrlwinganglio(ctrlWinGanglio ctrlwinganglio) {
		this.ctrlwinganglio = ctrlwinganglio;
	}

	public Window getWinEditGanglio() {
		return winEditGanglio;
	}

	public void setWinEditGanglio(Window winEditGanglio) {
		this.winEditGanglio = winEditGanglio;
	}

	public Ganglio getGanglio() {
		return ganglio;
	}

	public void setGanglio(Ganglio ganglio) {
		this.ganglio = ganglio;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditGanglio.setAttribute(comp.getId() + "ctrl", this);
		servicioGanglio = (ServicioGanglio) SpringUtil
				.getBean("beanServicioGanglio");
		ganglio = new Ganglio();
		ctrlwinganglio = (ctrlWinGanglio) arg.get("ctrlWinGanglio");
		if (!(arg.get("objeto") == null)) {
			ganglio = (Ganglio) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinganglio.recargar();
		ctrlwinganglio.apagarBotones();
		this.winEditGanglio.detach();
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
			System.out.println(ganglio.getNombre());
			Ganglio ganglioTemp = servicioGanglio
					.buscarUno(ganglio.getNombre());
			if (ganglioTemp != null) {
				ganglio.setId(ganglioTemp.getId());
			}

			try {
				servicioGanglio.guardarGanglio(ganglio);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinganglio.recargar();
						ctrlwinganglio.apagarBotones();
						winEditGanglio.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditGanglio() {
		ctrlwinganglio.apagarBotones();
		this.winEditGanglio.detach();
	}
}
