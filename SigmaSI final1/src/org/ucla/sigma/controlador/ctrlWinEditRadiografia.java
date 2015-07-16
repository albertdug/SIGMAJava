package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Radiografia;
import org.ucla.sigma.servicio.ServicioRadiografia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditRadiografia extends GenericForwardComposer {

	private Window winEditRadiografia;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioRadiografia servicioRadiografia;
	private ctrlWinRadiografia ctrlwinradiografia;

	// ----------------------------------------------------------------------------------------------------

	private Radiografia radiografia;

	// ----------------------------------------------------------------------------------------------------

	public ServicioRadiografia getServicioRadiografia() {
		return servicioRadiografia;
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

	public void setServicioRadiografia(ServicioRadiografia servicioRadiografia) {
		this.servicioRadiografia = servicioRadiografia;
	}

	public ctrlWinRadiografia getCtrlwinradiografia() {
		return ctrlwinradiografia;
	}

	public void setCtrlwinradiografia(ctrlWinRadiografia ctrlwinradiografia) {
		this.ctrlwinradiografia = ctrlwinradiografia;
	}

	public Window getWinEditRadiografia() {
		return winEditRadiografia;
	}

	public void setWinEditRadiografia(Window winEditRadiografia) {
		this.winEditRadiografia = winEditRadiografia;
	}

	public Radiografia getRadiografia() {
		return radiografia;
	}

	public void setRadiografia(Radiografia radiografia) {
		this.radiografia = radiografia;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditRadiografia.setAttribute(comp.getId() + "ctrl", this);
		servicioRadiografia = (ServicioRadiografia) SpringUtil
				.getBean("beanServicioRadiografia");
		radiografia = new Radiografia();
		ctrlwinradiografia = (ctrlWinRadiografia) arg.get("ctrlWinRadiografia");
		if (!(arg.get("objeto") == null)) {
			radiografia = (Radiografia) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinradiografia.recargar();
		ctrlwinradiografia.apagarBotones();
		this.winEditRadiografia.detach();
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
			Radiografia radiografiaTemp = servicioRadiografia.buscarUno(radiografia.getNombre());
			if (radiografiaTemp != null) {
				radiografia.setId(radiografiaTemp.getId());
			}

			try {
				servicioRadiografia.guardarRadiografia(radiografia);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinradiografia.recargar();
						ctrlwinradiografia.apagarBotones();
						winEditRadiografia.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditRadiografia() {
		ctrlwinradiografia.apagarBotones();
		this.winEditRadiografia.detach();
	}
}
