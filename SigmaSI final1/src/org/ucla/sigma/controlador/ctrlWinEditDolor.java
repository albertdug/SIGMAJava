package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.Dolor;
import org.ucla.sigma.servicio.ServicioDolor;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditDolor extends GenericForwardComposer {

	private Window winEditDolor;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioDolor servicioDolor;
	private ctrlWinDolor ctrlwindolor;

	// ----------------------------------------------------------------------------------------------------

	private Dolor dolor;

	// ----------------------------------------------------------------------------------------------------

	public ServicioDolor getServicioDolor() {
		return servicioDolor;
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

	public void setServicioDolor(ServicioDolor servicioDolor) {
		this.servicioDolor = servicioDolor;
	}

	public ctrlWinDolor getCtrlwinDolor() {
		return ctrlwindolor;
	}

	public void setCtrlwindolor(ctrlWinDolor ctrlwindolor) {
		this.ctrlwindolor = ctrlwindolor;
	}

	public Window getWinEditdolor() {
		return winEditDolor;
	}

	public void setWinEditDolor(Window winEditDolor) {
		this.winEditDolor = winEditDolor;
	}

	public Dolor getDolor() {
		return dolor;
	}

	public void setDolor(Dolor dolor) {
		this.dolor = dolor;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditDolor.setAttribute(comp.getId() + "ctrl", this);
		servicioDolor = (ServicioDolor) SpringUtil
				.getBean("beanServicioDolor");
		dolor = new Dolor();
		ctrlwindolor = (ctrlWinDolor) arg.get("ctrlWinDolor");
		if (!(arg.get("objeto") == null)) {
			dolor = (Dolor) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwindolor.apagarBotones();
		this.winEditDolor.detach();
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
			Dolor dolorTemp = servicioDolor
					.buscarUno(dolor.getNombre());
			if (dolorTemp != null) {
				dolor.setId(dolorTemp.getId());
			}

			try {
				servicioDolor.guardarDolor(dolor);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwindolor.recargar();
						ctrlwindolor.apagarBotones();
						winEditDolor.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditDolor() {
		ctrlwindolor.apagarBotones();
		this.winEditDolor.detach();
	}
}
