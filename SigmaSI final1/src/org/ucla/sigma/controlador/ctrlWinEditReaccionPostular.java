package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.ReaccionPostular;
import org.ucla.sigma.servicio.ServicioReaccionPostular;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditReaccionPostular extends GenericForwardComposer {

	private Window winEditReaccionPostular;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioReaccionPostular servicioReaccionPostular;
	private ctrlWinReaccionPostular ctrlwinreaccionPostular;

	// ----------------------------------------------------------------------------------------------------

	private ReaccionPostular reaccionPostular;

	// ----------------------------------------------------------------------------------------------------

	public ServicioReaccionPostular getServicioReaccionPostular() {
		return servicioReaccionPostular;
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

	public void setServicioReaccionPostular(ServicioReaccionPostular servicioReaccionPostular) {
		this.servicioReaccionPostular = servicioReaccionPostular;
	}

	public ctrlWinReaccionPostular getCtrlwinReaccionPostular() {
		return ctrlwinreaccionPostular;
	}

	public void setCtrlwinreaccionPostular(ctrlWinReaccionPostular ctrlwinreaccionPostular) {
		this.ctrlwinreaccionPostular = ctrlwinreaccionPostular;
	}

	public Window getWinEditReaccionPostular() {
		return winEditReaccionPostular;
	}

	public void setWinEditReaccionPostular(Window winEditReaccionPostular) {
		this.winEditReaccionPostular = winEditReaccionPostular;
	}

	public ReaccionPostular getReaccionPostular() {
		return reaccionPostular;
	}

	public void setReaccionPostular(ReaccionPostular reaccionPostular) {
		this.reaccionPostular = reaccionPostular;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditReaccionPostular.setAttribute(comp.getId() + "ctrl", this);
		servicioReaccionPostular = (ServicioReaccionPostular) SpringUtil
				.getBean("beanServicioReaccionPostular");
		reaccionPostular = new ReaccionPostular();
		ctrlwinreaccionPostular = (ctrlWinReaccionPostular) arg.get("ctrlWinReaccionPostular");
		if (!(arg.get("objeto") == null)) {
			reaccionPostular = (ReaccionPostular) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinreaccionPostular.apagarBotones();
		this.winEditReaccionPostular.detach();
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
			ReaccionPostular reaccionPostularTemp = servicioReaccionPostular
					.buscarUno(reaccionPostular.getNombre());
			if (reaccionPostularTemp != null) {
				reaccionPostular.setId(reaccionPostularTemp.getId());
			}

			try {
				servicioReaccionPostular.guardarReaccionPostular(reaccionPostular);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinreaccionPostular.recargar();
						ctrlwinreaccionPostular.apagarBotones();
						winEditReaccionPostular.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditReaccionPostular() {
		ctrlwinreaccionPostular.apagarBotones();
		this.winEditReaccionPostular.detach();
	}
}
