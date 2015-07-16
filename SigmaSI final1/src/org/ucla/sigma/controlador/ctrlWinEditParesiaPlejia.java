package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.ParesiaPlejia;
import org.ucla.sigma.servicio.ServicioParesiaPlejia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditParesiaPlejia extends GenericForwardComposer {

	private Window winEditParesiaPlejia;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioParesiaPlejia servicioParesiaPlejia;
	private ctrlWinParesiaPlejia ctrlwinparesiaPlejia;

	// ----------------------------------------------------------------------------------------------------

	private ParesiaPlejia paresiaPlejia;

	// ----------------------------------------------------------------------------------------------------

	public ServicioParesiaPlejia getServicioParesiaPlejia() {
		return servicioParesiaPlejia;
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

	public void setServicioParesiaPlejia(ServicioParesiaPlejia servicioParesiaPlejia) {
		this.servicioParesiaPlejia = servicioParesiaPlejia;
	}

	public ctrlWinParesiaPlejia getCtrlwinParesiaPlejia() {
		return ctrlwinparesiaPlejia;
	}

	public void setCtrlwinparesiaPlejia(ctrlWinParesiaPlejia ctrlwinparesiaPlejia) {
		this.ctrlwinparesiaPlejia = ctrlwinparesiaPlejia;
	}

	public Window getWinEditParesiaPlejia() {
		return winEditParesiaPlejia;
	}

	public void setWinEditParesiaPlejia(Window winEditParesiaPlejia) {
		this.winEditParesiaPlejia = winEditParesiaPlejia;
	}

	public ParesiaPlejia getParesiaPlejia() {
		return paresiaPlejia;
	}

	public void setParesiaPlejia(ParesiaPlejia paresiaPlejia) {
		this.paresiaPlejia = paresiaPlejia;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditParesiaPlejia.setAttribute(comp.getId() + "ctrl", this);
		servicioParesiaPlejia = (ServicioParesiaPlejia) SpringUtil
				.getBean("beanServicioParesiaPlejia");
		paresiaPlejia = new ParesiaPlejia();
		ctrlwinparesiaPlejia = (ctrlWinParesiaPlejia) arg.get("ctrlWinParesiaPlejia");
		if (!(arg.get("objeto") == null)) {
			paresiaPlejia = (ParesiaPlejia) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinparesiaPlejia.apagarBotones();
		this.winEditParesiaPlejia.detach();
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
			ParesiaPlejia paresiaPlejiaTemp = servicioParesiaPlejia
					.buscarUno(paresiaPlejia.getNombre());
			if (paresiaPlejiaTemp != null) {
				paresiaPlejia.setId(paresiaPlejiaTemp.getId());
			}

			try {
				servicioParesiaPlejia.guardarParesiaPlejia(paresiaPlejia);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinparesiaPlejia.recargar();
						ctrlwinparesiaPlejia.apagarBotones();
						winEditParesiaPlejia.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditParesiaPlejia() {
		ctrlwinparesiaPlejia.apagarBotones();
		this.winEditParesiaPlejia.detach();
	}
}
