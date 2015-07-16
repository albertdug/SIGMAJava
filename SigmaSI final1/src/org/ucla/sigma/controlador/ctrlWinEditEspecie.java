package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.servicio.ServicioEspecie;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditEspecie extends GenericForwardComposer {

	private Window winEditEspecie;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioEspecie servicioEspecie;
	private ctrlWinEspecie ctrlwinespecie;

	// ----------------------------------------------------------------------------------------------------

	private Especie especie;

	// ----------------------------------------------------------------------------------------------------

	public ServicioEspecie getServicioEspecie() {
		return servicioEspecie;
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

	public void setServicioEspecie(ServicioEspecie servicioEspecie) {
		this.servicioEspecie = servicioEspecie;
	}

	public ctrlWinEspecie getCtrlwinespecie() {
		return ctrlwinespecie;
	}

	public void setCtrlwinespecie(ctrlWinEspecie ctrlwinespecie) {
		this.ctrlwinespecie = ctrlwinespecie;
	}

	public Window getWinEditEspecie() {
		return winEditEspecie;
	}

	public void setWinEditEspecie(Window winEditEspecie) {
		this.winEditEspecie = winEditEspecie;
	}

	public Especie getEspecie() {
		return especie;
	}

	public void setEspecie(Especie especie) {
		this.especie = especie;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditEspecie.setAttribute(comp.getId() + "ctrl", this);
		servicioEspecie = (ServicioEspecie) SpringUtil
				.getBean("beanServicioEspecie");
		especie = new Especie();
		ctrlwinespecie = (ctrlWinEspecie) arg.get("ctrlWinEspecie");
		if (!(arg.get("objeto") == null)) {
			especie = (Especie) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinespecie.recargar();
		ctrlwinespecie.apagarBotones();
		this.winEditEspecie.detach();
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
			Especie especieTemp = servicioEspecie
					.buscarUno(especie.getNombre());
			if (especieTemp != null) {
				especie.setId(especieTemp.getId());
			}

			try {
				servicioEspecie.guardarEspecie(especie);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinespecie.recargar();
						ctrlwinespecie.apagarBotones();
						winEditEspecie.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditEspecie() {
		ctrlwinespecie.apagarBotones();
		this.winEditEspecie.detach();
	}
}
