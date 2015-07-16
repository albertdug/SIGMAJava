/**
 * 
 */
package org.ucla.sigma.controlador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.TipoTratamiento;
import org.ucla.sigma.servicio.ServicioEspecie;
import org.ucla.sigma.servicio.ServicioTipoTratamiento;
import org.zkoss.zkplus.spring.SpringUtil;

/**
 * @author usuario
 * 
 */
public class ctrlWinEditTipoTratamiento extends GenericForwardComposer {

	private Window winEditTipoTratamiento;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioTipoTratamiento servicioTipoTratamiento;
	private ctrlWinTipoTratamiento ctrlwintipoTratamiento;

	// ----------------------------------------------------------------------------------------------------

	private TipoTratamiento tipoTratamiento;

	// ----------------------------------------------------------------------------------------------------

	public ServicioTipoTratamiento getServicioTipoTratamiento() {
		return servicioTipoTratamiento;
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

	public void setServicioTipoTratamiento(
			ServicioTipoTratamiento servicioTipoTratamiento) {
		this.servicioTipoTratamiento = servicioTipoTratamiento;
	}

	public ctrlWinTipoTratamiento getCtrlwintipoTratamiento() {
		return ctrlwintipoTratamiento;
	}

	public void setCtrlwintipoTratamiento(
			ctrlWinTipoTratamiento ctrlwintipoTratamiento) {
		this.ctrlwintipoTratamiento = ctrlwintipoTratamiento;
	}

	public Window getWinEditTipoTratamiento() {
		return winEditTipoTratamiento;
	}

	public void setWinEditTipoTratamiento(Window winEditTipoTratamiento) {
		this.winEditTipoTratamiento = winEditTipoTratamiento;
	}

	public TipoTratamiento getTipoTratamiento() {
		return tipoTratamiento;
	}

	public void setTipoTratamiento(TipoTratamiento tipoTratamiento) {
		this.tipoTratamiento = tipoTratamiento;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditTipoTratamiento.setAttribute(comp.getId() + "ctrl", this);
		servicioTipoTratamiento = (ServicioTipoTratamiento) SpringUtil
				.getBean("beanServicioTipoTratamiento");
		tipoTratamiento = new TipoTratamiento();
		ctrlwintipoTratamiento = (ctrlWinTipoTratamiento) arg
				.get("ctrlWinTipoTratamiento");
		if (!(arg.get("objeto") == null)) {
			tipoTratamiento = (TipoTratamiento) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwintipoTratamiento.recargar();
		ctrlwintipoTratamiento.apagarBotones();
		this.winEditTipoTratamiento.detach();
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
			TipoTratamiento tipoTratamientoTemp = servicioTipoTratamiento
					.buscarUno(tipoTratamiento.getNombre());
			if (tipoTratamientoTemp != null) {
				tipoTratamiento.setId(tipoTratamientoTemp.getId());
			}

			try {
				servicioTipoTratamiento.guardarTipoTratamiento(tipoTratamiento);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwintipoTratamiento.recargar();
						ctrlwintipoTratamiento.apagarBotones();
						winEditTipoTratamiento.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditTipoTratamiento() {
		ctrlwintipoTratamiento.apagarBotones();
		this.winEditTipoTratamiento.detach();
	}
}
